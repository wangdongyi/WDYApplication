#include <jni.h>
#include <string>
#include <android/log.h>
/**
 * 作者：王东一
 * 创建时间：2017/7/3.
 * txt文件阅读
 */
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
extern "C" {
JNIEXPORT jstring JNICALL
Java_com_base_library_util_TxtReadUtil_TxtRead(JNIEnv *env, jclass cla, jstring path) {
    const char *jpath = env->GetStringUTFChars(path, NULL);
    FILE *pFile;
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
}


