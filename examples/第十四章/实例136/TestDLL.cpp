package 第十四章.实例136;
#include"TestDLL.h"

JNIEXPORT void JNICALL Java_TestDLL_helloWorld(JNIEnv *env, jclass obj){
	printf("hello world!\n");  //在此处实现java类中定义的方法
	return;
}

JNIEXPORT jstring JNICALL Java_TestDLL_cTOJava(JNIEnv *env, jclass obj){
	jstring jstr;
	char str[] = "hello c and java\n";
	jstr = env->NewStringUTF(str);
	return jstr;
}

