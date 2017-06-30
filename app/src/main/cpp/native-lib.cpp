#include <jni.h>
#include <string>
#include <android/log.h>

#define TAG "wdy-jni" // 这个是自定义的LOG的标识
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,TAG ,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG ,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,TAG ,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,TAG ,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,TAG ,__VA_ARGS__) // 定义LOGF类型

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


