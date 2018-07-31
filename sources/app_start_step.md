
APP启动流程


#### 整个应用程序的启动过程要执行很多步骤，但是整体来看，主要分为以下五个阶段：

    
    一. Step1 - Step 11：
    Launcher通过Binder进程间通信机制通知ActivityManagerService，
    它要启动一个Activity；
    
    二. Step 12 - Step 16：
    ActivityManagerService通过Binder进程间通信机制通知Launcher进入Paused状态；
    
    三. Step 17 - Step 24：
    Launcher通过Binder进程间通信机制通知ActivityManagerService，它已经准备就绪进入Paused状态，
    于是ActivityManagerService就创建一个新的进程，用来启动一个ActivityThread实例，
    即将要启动的Activity就是在这个ActivityThread实例中运行；
    
    四. Step 25 - Step 27：
    ActivityThread通过Binder进程间通信机制将一个ApplicationThread类型的Binder对象传递给ActivityManagerService，
    以便以后ActivityManagerService能够通过这个Binder对象和它进行通信；
    
    五. Step 28 - Step 35：
    ActivityManagerService通过Binder进程间通信机制通知ActivityThread，
    现在一切准备就绪，它可以真正执行Activity的启动操作了。
    。