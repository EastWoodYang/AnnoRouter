package com.eastwood.common.router;

import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;

class ParamHelper {

    static public void putIntent(Bundle bundle, RouterInfo routerInfo) {
        if (routerInfo.paramNames == null) return;

        int count = routerInfo.paramNames.length;
        for (int i = 0; i < count; i++) {
            String name = routerInfo.paramNames[i];
            Object type = routerInfo.paramTypes[i];
            Object value = routerInfo.paramValues[i];
            putIntent(bundle, name, type, value);
        }
        return;
    }

    private static void putIntent(Bundle bundle, String name, Object type, Object value) {
        if (value == null) {
            if (((Class) type).isPrimitive()) {
                // not support.
            } else {
                bundle.putSerializable(name, null);
            }
        } else if (value.getClass() == String.class) {
            if (type == String.class) {
                bundle.putString(name, (String) value);
            } else if (((Class) type).isPrimitive()) {
                if (type == int.class) {
                    bundle.putInt(name, Integer.parseInt((String) value));
                } else if (type == short.class) {
                    bundle.putShort(name, Short.parseShort((String) value));
                } else if (type == long.class) {
                    bundle.putLong(name, Long.parseLong((String) value));
                } else if (type == double.class) {
                    bundle.putDouble(name, Double.valueOf((String) value));
                } else if (type == float.class) {
                    bundle.putFloat(name, Float.parseFloat((String) value));
                } else if (type == boolean.class) {
                    bundle.putBoolean(name, Boolean.valueOf((String) value));
                } else if (type == byte.class) {
                    bundle.putByte(name, Byte.valueOf((String) value));
                } else if (type == char.class) {
                    char[] cs = ((String) value).toCharArray();
                    bundle.putChar(name, cs[0]);
                }
            } else {
                if (type == Integer.class) {
                    bundle.putSerializable(name, Integer.valueOf((String) value));
                } else if (type == Short.class) {
                    bundle.putSerializable(name, Short.valueOf((String) value));
                } else if (type == Long.class) {
                    bundle.putSerializable(name, Long.valueOf((String) value));
                } else if (type == Double.class) {
                    bundle.putSerializable(name, Double.valueOf((String) value));
                } else if (type == Float.class) {
                    bundle.putSerializable(name, Float.valueOf((String) value));
                } else if (type == Boolean.class) {
                    bundle.putSerializable(name, Boolean.valueOf((String) value));
                } else if (type == Byte.class) {
                    bundle.putSerializable(name, Byte.valueOf((String) value));
                } else if (type == Character.class) {
                    char[] cs = ((String) value).toCharArray();
                    bundle.putSerializable(name, cs[0]);
                } else {
                    // not support.
                }
            }
        } else if (((Class) type).isPrimitive()) {
            if (type == int.class) {
                bundle.putInt(name, (int) value);
            } else if (type == short.class) {
                bundle.putShort(name, (short) value);
            } else if (type == long.class) {
                bundle.putLong(name, (long) value);
            } else if (type == double.class) {
                bundle.putDouble(name, (double) value);
            } else if (type == float.class) {
                bundle.putFloat(name, (float) value);
            } else if (type == boolean.class) {
                bundle.putBoolean(name, (boolean) value);
            } else if (type == byte.class) {
                bundle.putByte(name, (byte) value);
            } else if (type == char.class) {
                bundle.putChar(name, (char) value);
            }
        } else if (value.getClass().isArray()) {
            if (((Class) type).getComponentType().isPrimitive()) {
                if (type == int[].class) {
                    bundle.putIntArray(name, (int[]) value);
                } else if (type == short[].class) {
                    bundle.putShortArray(name, (short[]) value);
                } else if (type == long[].class) {
                    bundle.putLongArray(name, (long[]) value);
                } else if (type == double[].class) {
                    bundle.putDoubleArray(name, (double[]) value);
                } else if (type == float[].class) {
                    bundle.putFloatArray(name, (float[]) value);
                } else if (type == boolean[].class) {
                    bundle.putBooleanArray(name, (boolean[]) value);
                } else if (type == byte[].class) {
                    bundle.putByteArray(name, (byte[]) value);
                } else if (type == char[].class) {
                    bundle.putCharArray(name, (char[]) value);
                }
            } else {
                if (type == String[].class) {
                    bundle.putStringArray(name, (String[]) value);
                } else if (type == Parcelable[].class) {
                    bundle.putParcelableArray(name, (Parcelable[]) value);
                } else if (type == CharSequence[].class) {
                    bundle.putCharSequenceArray(name, (CharSequence[]) value);
                } else {
                    bundle.putSerializable(name, (Serializable) value);
                }
            }
        } else if (value instanceof Serializable) {
            bundle.putSerializable(name, (Serializable) value);
        } else if (value instanceof Parcelable) {
            bundle.putParcelable(name, (Parcelable) value);
        } else if (type == CharSequence.class) {
            bundle.putCharSequence(name, (CharSequence) value);
        } else {
            throw new IllegalArgumentException(value.getClass().toString() + ": This type of parameter isn't supported.");
        }
    }

}