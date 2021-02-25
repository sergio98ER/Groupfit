/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.mtp;

import android.annotation.Nullable;
import android.content.Context;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.mtp.MtpConstants;
import android.mtp.MtpDevice;
import android.mtp.MtpDeviceInfo;
import android.mtp.MtpEvent;
import android.mtp.MtpObjectInfo;
import android.mtp.MtpStorageInfo;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.util.SparseArray;

import com.android.internal.annotations.VisibleForTesting;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The model wrapping android.mtp API.
 */
class MtpManager {
    final static int OBJECT_HANDLE_ROOT_CHILDREN = -1;

    /**
     * Subclass for PTP.
     */
    private static final int SUBCLASS_STILL_IMAGE_CAPTURE = 1;

    /**
     * Subclass for Android style MTP.
     */
    private static final int SUBCLASS_MTP = 0xff;

    /**
     * Protocol for Picture Transfer Protocol (PIMA 15470).
     */
    private static final int PROTOCOL_PICTURE_TRANSFER = 1;

    /**
     * Protocol for Android style MTP.
     */
    private static final int PROTOCOL_MTP = 0;

    private final UsbManager mManager;
    private final SparseArray<MtpDevice> mDevices = new SparseArray<>();

    MtpManager(Context context) {
        mManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
    }

    synchronized MtpDeviceRecord openDev