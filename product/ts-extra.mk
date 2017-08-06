PRODUCT_PACKAGES += \
	TsExtra

ifneq (,$(filter eng, $(TARGET_BUILD_VARIANT)))
# Insecure adb
ADDITIONAL_DEFAULT_PROPERTIES += ro.adb.secure=0
ADDITIONAL_DEFAULT_PROPERTIES += ro.secure=0
endif

# TS
PRODUCT_PROPERTY_OVERRIDES += \
	ro.ts.build=ts-aospa7
