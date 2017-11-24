#include <jni.h>
#include <string>
#include <android/log.h>
#include<malloc.h>
#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <string.h>

#define TAG "wdy-jni" // 这个是自定义的LOG的标识
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,TAG ,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,TAG ,__VA_ARGS__) // 定义LOGF类型
#define MAX_LINE 1024
extern "C"
JNIEXPORT void JNICALL
Java_com_wdy_project_MainActivity_JniLog(JNIEnv *env, jclass cla, jstring js) {
    const char *s = env->GetStringUTFChars(js, NULL);

    LOGD(s, TAG);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_wdy_project_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
const char *ccpath = "/sdcard/HUDSDKLog.txt";

char* jStringToChar(JNIEnv* env, jstring jstr) ;

extern "C"
JNIEXPORT jstring JNICALL
Java_com_wdy_project_MainActivity_textFromJNI(JNIEnv *env, jclass cla, jstring path) {
    const char *jpath = env->GetStringUTFChars(path, NULL);
    FILE *pFile;
    LOGD(jpath, TAG);
    LOGD(ccpath, TAG);
    pFile = fopen(jpath, "rw");
    if (pFile == NULL) {
        return env->NewStringUTF("文件读出失败");
    }
    char *pBuf;  //定义文件指针
    fseek(pFile, 0, SEEK_END); //把指针移动到文件的结尾 ，获取文件长度
    int size = (int) ftell(pFile); //获取文件长度
    pBuf = new char[size + 1]; //定义数组长度
    rewind(pFile); //把指针移动到文件开头 因为我们一开始把指针移动到结尾，如果不移动回来 会出错
    fread(pBuf, 1, (size_t) size, pFile); //读文件
    pBuf[size] = 0; //把读到的文件最后一位 写为0 要不然系统会一直寻找到0后才结束
    fclose(pFile); // 关闭文件
    return env->NewStringUTF(pBuf);
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_wdy_project_MainActivity_dataFromJNI(JNIEnv *env, jobject clazz, jint bufID, jint priority,
                                              jstring tagObj, jstring msgObj) {
    const char *tag = NULL;
    const char *msg = NULL;

    if (msgObj == NULL) {   //异常处理
        jclass npeClazz;

        npeClazz = env->FindClass("java/lang/NullPointerException");
        //抛出异常
        env->ThrowNew(npeClazz, "println needs a message");
        return -1;
    }

    if (bufID < 0 || bufID >= 100) {
        jclass npeClazz;

        npeClazz = env->FindClass("java/lang/NullPointerException");

        env->ThrowNew(npeClazz, "bad bufID");
        return -1;
    }

    if (tagObj != NULL)
        tag = env->GetStringUTFChars(tagObj, NULL);
    msg = env->GetStringUTFChars(msgObj, NULL);
    //向内核写入日志
//    int res = __android_log_buf_write(bufID, (android_LogPriority) priority, tag, msg);
    int res = 1;
    if (tag != NULL)
        env->ReleaseStringUTFChars(tagObj, tag);
    env->ReleaseStringUTFChars(msgObj, msg);
    return res;
}
char* jStringToChar(JNIEnv* env, jstring jstr) {
    char* rtn = NULL;
    jclass clsstring = env->FindClass("java/lang/String");
    jstring strencode = env->NewStringUTF("GB2312");
    jmethodID mid = env->GetMethodID(clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr = (jbyteArray) env->CallObjectMethod(jstr, mid, strencode);
    jsize alen = env->GetArrayLength(barr);
    jbyte* ba = env->GetByteArrayElements(barr, JNI_FALSE);
    if (alen > 0) {
        rtn = (char*) malloc((size_t) (alen + 1));
        memcpy(rtn, ba, (size_t) alen);
        rtn[alen] = 0;
    }
    env->ReleaseByteArrayElements(barr, ba, 0);
    return rtn;
}

