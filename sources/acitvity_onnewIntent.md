### Activity的onNewIntent()方法何时会被调用? 



前提:ActivityA已经启动过,处于当前应用的Activity堆栈中; 

当ActivityA的LaunchMode为SingleTop时，如果ActivityA在栈顶,且现在要再启动ActivityA，这时会调用onNewIntent()方法 

当ActivityA的LaunchMode为SingleInstance,SingleTask时,如果已经ActivityA已经在堆栈中，那么此时会调用onNewIntent()方法 

当ActivityA的LaunchMode为Standard时，由于每次启动ActivityA都是启动新的实例，和原来启动的没关系，所以不会调用原来ActivityA的onNewIntent方法