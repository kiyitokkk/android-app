//
// Created by ting on 2019-09-17.
//

#include <jni.h>
#include <string>
#include <assert.h>
#include "aes_utils.h"
#include "tools.h"
#include "junk.h"
#include "MD5.h"
#include <iostream>
#include <cstdint>
#include <dlfcn.h>
#include <android/log.h>
#include <sys/ptrace.h>
#include <unistd.h>
#define JNIREG_CLASS "com/example/job/util/Cyber"
#define NELEM(x) ((int) (sizeof(x) / sizeof((x)[0])))
#include <sys/ptrace.h>
#include <android/log.h>
const char * LOG_TAG = "LOG_TGA";

#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define LOGT(...) __android_log_print(ANDROID_LOG_INFO,"alert",__VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#define BUFFER_SIZE 1024

#ifdef __cplusplus
extern "C" {
#endif
static const char *CHECK_FEATURE = "/tmp";
static const char *TAG = "ANTI_FRIDA";
static const char *PROC_MAPS = "/proc/self/maps";
bool detectFrida() {
    FILE* mapsFile = fopen("/proc/self/maps", "r");
    if (mapsFile == NULL) {
        perror("Failed to open /proc/self/maps");
        return true;
    }
    char buffer[BUFFER_SIZE];
    int isFridaDetected = 0;
    while (fgets(buffer, BUFFER_SIZE, mapsFile) != NULL) {
        if (strstr(buffer, "frida") != NULL) {
            isFridaDetected = 0;
            return false;
        }
    }
    fclose(mapsFile);
    return true;
}


void anti_debug()
{
    int pid = getpid();
    int ppid = getppid();
    LOGE("pid:%d,ppid:%d",pid,ppid);
}

bool isHooked(const char* functionName) {
    // 使用dlsym函数获取函数指针
    void* functionPtr = dlsym(RTLD_DEFAULT, functionName);

    if (functionPtr == nullptr) {
        // 函数未找到，可能被Hook
        return true;
    }

    // 检查函数指针是否与预期的符号地址匹配
    void* expectedPtr = (void*)dlsym(RTLD_NEXT, functionName);
    if (functionPtr != expectedPtr) {
        // 函数指针不匹配，可能被Hook
        return true;
    }

    return false; // 未检测到Hook
}

bool isDebugged() {
    int status = 0;
    if (ptrace(PTRACE_TRACEME, 0, nullptr, 0) == -1) {
        // 如果 ptrace 调用失败，则表示正在被调试
        return true;
    }
    return false; // 未检测到调试
}
char* concatenateStrings(const char* str1, const char* str2) {
    size_t str1Length = strlen(str1);
    size_t str2Length = strlen(str2);
    size_t combinedLength = str1Length + str2Length;
    char* result = new char[combinedLength + 1];
    strncpy(result, str1, str1Length);
    strncpy(result + str1Length, str2, str2Length);
    result[combinedLength] = '\0';

    return result;
}


JNIEXPORT jstring JNICALL Sub_653(JNIEnv *env, jclass jcls, jstring str_) {
    if (str_ == nullptr) return nullptr;
    const char *str = env->GetStringUTFChars(str_, JNI_FALSE);
    junk_fun0();
    junk_fun1();
    junk_fun2();
    MD5 md5 = MD5(str);
    std::string md5Result = md5.hexdigest();
    jstring jstring2 = env->NewStringUTF(md5Result.c_str());
    const char *str2 = (env->GetStringUTFChars(jstring2, JNI_FALSE));
    const char *str3 =  concatenateStrings(str, str2);
    char *result = AES_128_CBC_PKCS5_Encrypt(str3);
    jstring jsResult1 = getJString(env, result);
    junk_fun0();
    junk_fun1();
    junk_fun2();
    return jsResult1 ;
}
JNIEXPORT jstring JNICALL Sub_654(JNIEnv *env, jclass jcls, jstring str_) {
    const char *originStr;
    //将jstring转化成char *类型
    originStr = env->GetStringUTFChars(str_, JNI_FALSE);
    MD5 md5 = MD5(originStr);
    junk_fun3();
    //将char *类型转化成jstring返回给Java层
    junk_fun0();
    std::string md5Result;
    junk_fun1();
    junk_fun2();
    if (!detectFrida()){
        volatile int* crash = nullptr;
        *crash = 42;  // 在空指针上解引用，导致崩溃
    }
    md5Result  = md5.hexdigest();
    return env->NewStringUTF(md5Result.c_str());
}
static JNINativeMethod method_table[] = {
        {"hello1", "(Ljava/lang/String;)Ljava/lang/String;", (void *) Sub_653},
        {"hello2", "(Ljava/lang/String;)Ljava/lang/String;", (void *) Sub_654},
};

static int registerMethods(JNIEnv *env, const char *className,
                           JNINativeMethod *gMethods, int numMethods) {
    jclass clazz = env->FindClass(className);
    if (clazz == nullptr) {
        return JNI_FALSE;
    }
    if (env->RegisterNatives(clazz, gMethods, numMethods) < 0) {
        return JNI_FALSE;
    }
    return JNI_TRUE;
}
//JNIEXPORT jstring JNICALL
//Java_com_example_job_util_FingerPrint_getDeviceid(JNIEnv *env, jclass clazz) {
//    jclass buildClass = env->FindClass("android/os/Build");
//    jfieldID fingerprintField = env->GetStaticFieldID(buildClass, "FINGERPRINT",
//                                                      "Ljava/lang/String;");
//    jstring fingerprintString = static_cast<jstring>(env->GetStaticObjectField(buildClass, fingerprintField));
//
//    // 将设备指纹信息转换为C字符串
//    const char* fingerprint = env->GetStringUTFChars(fingerprintString, nullptr);
//
//    // 创建C字符串的副本，以便稍后释放资源
//    char* fingerprintCopy = strdup(fingerprint);
//
//    // 释放设备指纹信息的资源
//    env->ReleaseStringUTFChars(fingerprintString, fingerprint);
//
//    // 返回设备指纹信息的副本
//    return env->NewStringUTF(fingerprintCopy);
//}
JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    _JUNK_FUN_0
    JNIEnv *env = nullptr;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }
    assert(env != nullptr);

    // 注册native方法
    if (!registerMethods(env, JNIREG_CLASS, method_table, NELEM(method_table))) {
        return JNI_ERR;
    }

    return JNI_VERSION_1_6;
}


#ifdef __cplusplus
}
#endif


