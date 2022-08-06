package com.demo;

public class Application
{
    public static void main(String args[]) {
        System.out.println("[main] length: " + args.length);
        for (int i = 0; i < args.length; i++) {
            System.out.println("[main][" + i + "] " + args[i]);
        }
    }

    public static void PrintMessage(String msg) {
        System.out.println("[Message] " + msg);
    }

    public static int SetAddress(Address addr) {
        System.out.println("[Address]" +
            " name: " + addr.name +
            " ip: " + addr.ip +
            " port: " + addr.port);
        return 123;
    }

    public static void SetWorkers(Worker workers[]) {
        System.out.println("[Workers] length: " + workers.length);
        for (int i = 0; i < workers.length; i++) {
            Worker worker = workers[i];
            System.out.println("[Worker][" + i + "]" +
                " serial: " + worker.serial +
                " number: " + worker.number +
                " action: " + worker.action +
                " date: " + worker.date +
                " flag: " + worker.flag +
                " reason: " + worker.reason +
                " source: " + worker.source);
        }
    }

    public static Object BuildResponse() {
        System.out.println("[BuildResponse] prepare");
        return new Response(1, "Success!");
    }
}