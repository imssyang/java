#include <jni.h>
#include <stdio.h>
#include <string.h>

typedef struct Address {
	int id;
	char name[256];
	char ip[256];
	int port;
} Address;

typedef struct Worker {
	char serial[64];
	char number[64];
	char action[32];
	char date[32];
	char flag[8];
	char reason[256];
	char source[128];
} Worker;

void create_vm(JavaVM **jvm, JNIEnv **env) {
    JavaVMOption options;
	options.optionString = (char*)"-Djava.class.path=/opt/java/sample/CJNIJava/out/demo.jar";

    JavaVMInitArgs args;
    args.version = JNI_VERSION_1_6;
    args.nOptions = 1;
    args.options = &options;
    args.ignoreUnrecognized = 0;
    int ret = JNI_CreateJavaVM(jvm, (void**)env, &args);
    if (ret < 0)
    	printf("<create_vm> Unable to launch JVM\n");
}

void init_workers(Worker workers[]) {
	strcpy(workers[0].serial, "2000");
	strcpy(workers[0].number, "2878430");
	strcpy(workers[0].action, "04");
	strcpy(workers[0].date, "25-12-2007 12:20:30 PM");
	strcpy(workers[0].flag, "0");
	strcpy(workers[0].reason, "Executed Successfully");
	strcpy(workers[0].source, "PMS");
	strcpy(workers[1].serial, "1000");
	strcpy(workers[1].number, "2878000");
	strcpy(workers[1].action, "T4");
	strcpy(workers[1].date, "25-12-2007 11:20:30 PM");
	strcpy(workers[1].flag, "0");
	strcpy(workers[1].reason, "");
	strcpy(workers[1].source, "RMS");
	printf("<init_workers> serial_0: %s serial_1: %s\n",
		workers[0].serial, workers[1].serial);
}

void init_address(Address& addr) {
	addr.id = 11;
	addr.port = 9099;
	strcpy(addr.name,"HR-HW");
	strcpy(addr.ip,"10.32.164.133");
	printf("<init_address> id: %d name: %s ip: %s port: %d\n",
		addr.id, addr.name, addr.ip, addr.port);
}

int main(int argc, char* argv[]) {
	JNIEnv *env;
	JavaVM *jvm;
	create_vm(&jvm, &env);
	if (!jvm || !env)
		return 0;

	Address addr;
	init_address(addr);

	struct Worker workers[2];
	init_workers(workers);

    jclass clsApp = env->FindClass("com/demo/Application");
	if (clsApp) {
		jmethodID mthMain = env->GetStaticMethodID(clsApp, "main", "([Ljava/lang/String;)V");
		if (mthMain) {
			jclass clsString = env->FindClass("java/lang/String");
			jmethodID mthInitString = env->GetMethodID(clsString, "<init>", "(Ljava/lang/String;)V");
			jobjectArray jargs = env->NewObjectArray(2, clsString, NULL);
			jobject jarg1 = env->NewObject(clsString, mthInitString, env->NewStringUTF("-a 1"));
			jobject jarg2 = env->NewObject(clsString, mthInitString, env->NewStringUTF("-b 2"));
			env->SetObjectArrayElement(jargs, 0, jarg1);
			env->SetObjectArrayElement(jargs, 1, jarg2);
			env->CallStaticVoidMethod(clsApp, mthMain, jargs);
		}

		jmethodID mthPrintMessage = env->GetStaticMethodID(clsApp, "PrintMessage","(Ljava/lang/String;)V");
		if (mthPrintMessage) {
			jstring msg = env->NewStringUTF("From C Program");
			env->CallStaticVoidMethod(clsApp, mthPrintMessage, msg);
		}

		jmethodID mthSetAddress = env->GetStaticMethodID(clsApp, "SetAddress","(Lcom/demo/Address;)I");
		if (mthSetAddress) {
			jclass clsAddr = env->FindClass("com/demo/Address");
			if (clsAddr) {
				jmethodID mthInitAddr = env->GetMethodID(clsAddr, "<init>", "(ILjava/lang/String;Ljava/lang/String;I)V");
				if (mthInitAddr) {
					jstring jname = env->NewStringUTF(addr.name);
					jstring jip = env->NewStringUTF(addr.ip);
					jobject jaddr = env->NewObject(clsAddr, mthInitAddr, addr.id, jname, jip, addr.port);
					jint jret = env->CallStaticIntMethod(clsApp, mthSetAddress, jaddr);
					printf("<main> SetAddress return %d\n", jret);
				}
			}
		}

		jmethodID mthSetWorkers = env->GetStaticMethodID(clsApp, "SetWorkers","([Lcom/demo/Worker;)V");
		if (mthSetWorkers) {
			jclass clsWorker = env->FindClass("com/demo/Worker");
			if (clsWorker) {
				jmethodID mthInitWorker = env->GetMethodID(clsWorker, "<init>",
					"(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
				if (mthInitWorker) {
					jobjectArray jworkers = env->NewObjectArray(2, clsWorker,
												env->NewObject(clsWorker, mthInitWorker,
													env->NewStringUTF(""),
													env->NewStringUTF(""),
													env->NewStringUTF(""),
													env->NewStringUTF(""),
													env->NewStringUTF(""),
													env->NewStringUTF(""),
													env->NewStringUTF("")));
					for(int i = 0; i < 2; i++) {
						env->SetObjectArrayElement(jworkers, i, env->NewObject(clsWorker, mthInitWorker,
																	env->NewStringUTF(workers[i].serial),
																	env->NewStringUTF(workers[i].number),
																	env->NewStringUTF(workers[i].action),
																	env->NewStringUTF(workers[i].date),
																	env->NewStringUTF(workers[i].flag),
																	env->NewStringUTF(workers[i].reason),
																	env->NewStringUTF(workers[i].source)));
					}
					env->CallStaticVoidMethod(clsWorker, mthSetWorkers, jworkers);
				}
			}
		}

		jmethodID mthBuildResponse = env->GetStaticMethodID(clsApp, "BuildResponse","()Ljava/lang/Object;");
		if (mthBuildResponse) {
			jobject jresp = env->CallStaticObjectMethod(clsApp, mthBuildResponse, NULL);
			jclass clsResp = env->GetObjectClass(jresp);
			jint jvalue = env->GetIntField(jresp, env->GetFieldID(clsResp, "value", "I"));
			jstring jdesc = (jstring)env->GetObjectField(jresp, env->GetFieldID(clsResp, "desc", "Ljava/lang/String;"));
			const char *desc = env->GetStringUTFChars(jdesc, 0);
			printf("<main> BuildResponse return value: %d desc: %s\n", jvalue, desc);
			env->ReleaseStringUTFChars(jdesc, desc);
		}
	}

	jvm->DestroyJavaVM();
    return 0;
}
