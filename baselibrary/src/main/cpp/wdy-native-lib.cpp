#include <jni.h>
#include <string>
#include <android/log.h>

#define TAG "wdy-jni" // 这个是自定义的LOG的标识
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,__VA_ARGS__ ,__VA_ARGS__) // 定义LOGD类型
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,__VA_ARGS__ ,__VA_ARGS__) // 定义LOGI类型
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,__VA_ARGS__ ,__VA_ARGS__) // 定义LOGW类型
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,__VA_ARGS__ ,__VA_ARGS__) // 定义LOGE类型
#define LOGF(...) __android_log_print(ANDROID_LOG_FATAL,__VA_ARGS__ ,__VA_ARGS__) // 定义LOGF类型


extern "C" {
JNIEXPORT void JNICALL
Java_com_base_library_util_WDYLog_JniLogD(JNIEnv *env, jclass cla, jstring tag, jstring content) {
    const char *jTag = env->GetStringUTFChars(tag, NULL);
    const char *jContent = env->GetStringUTFChars(content, NULL);
    LOGD(jTag, jContent);
}
}
extern "C" {
JNIEXPORT void JNICALL
Java_com_base_library_util_WDYLog_JniLogI(JNIEnv *env, jclass cla, jstring tag, jstring content) {
    const char *jTag = env->GetStringUTFChars(tag, NULL);
    const char *jContent = env->GetStringUTFChars(content, NULL);
    LOGI(jTag, jContent);
}
}
extern "C" {
JNIEXPORT void JNICALL
Java_com_base_library_util_WDYLog_JniLogW(JNIEnv *env, jclass cla, jstring tag, jstring content) {
    const char *jTag = env->GetStringUTFChars(tag, NULL);
    const char *jContent = env->GetStringUTFChars(content, NULL);
    LOGW(jTag, jContent);
}
}
extern "C" {
JNIEXPORT void JNICALL
Java_com_base_library_util_WDYLog_JniLogE(JNIEnv *env, jclass cla, jstring tag, jstring content) {
    const char *jTag = env->GetStringUTFChars(tag, NULL);
    const char *jContent = env->GetStringUTFChars(content, NULL);
    LOGE(jTag, jContent);
}
}
extern "C" {
JNIEXPORT void JNICALL
Java_com_base_library_util_WDYLog_JniLogF(JNIEnv *env, jclass cla, jstring tag, jstring content) {
    const char *jTag = env->GetStringUTFChars(tag, NULL);
    const char *jContent = env->GetStringUTFChars(content, NULL);
    LOGF(jTag, jContent);
}
}


