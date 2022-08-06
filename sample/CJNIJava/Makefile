
env:
	mkdir -p out

jar: env
	javac -source 1.6 -target 1.6 -nowarn \
		-encoding UTF-8 \
		-d out \
		java/com/demo/*.java
	jar cvfm out/demo.jar java/manifest -C out com/demo
	rm -rf out/com
	javadoc -public -verbose -encoding UTF-8 \
		-charset UTF-8 \
		-docencoding UTF-8 \
		-windowtitle "Demo" \
		-d doc \
		-sourcepath java \
		com.demo

demo: jar
	java -jar out/demo.jar

main: jar
	g++ -o out/main cpp/main.cpp \
		-I/opt/java/include \
		-I/opt/java/include/linux \
		-L/opt/java/jre/lib/amd64/server \
		-L/opt/java/jre/lib/amd64/jli \
		-ljli \
		-ljvm
	./out/main

clean:
	rm -rf out doc
