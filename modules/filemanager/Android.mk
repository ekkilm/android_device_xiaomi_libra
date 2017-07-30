LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE       := FileManager
LOCAL_MODULE_TAGS  := optional
LOCAL_MODULE_CLASS := APPS
LOCAL_SRC_FILES    := com.simplemobiletools.filemanager_32.apk
LOCAL_CERTIFICATE  := PRESIGNED
LOCAL_MODULE_PATH  := $(TARGET_OUT_APPS)
include $(BUILD_PREBUILT)
