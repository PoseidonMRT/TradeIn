package com.tt.tradein.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ToolsUtils {
    /**
     * 判断电话号码是否有效
     * 移动：134、135、136、137、138、139、147、150、151、152、157、158、159、182、183、187、188
     * 联通：130、131、132、145、155、156、185、186
     * 电信：133、153、180、181、189
     * 虚拟运营商：17x
     */
    public static boolean isMobileNO(String number) {
        if (number.startsWith("+86")) {
            number = number.substring(3);
        }

        if (number.startsWith("+") || number.startsWith("0")) {
            number = number.substring(1);
        }

        number = number.replace(" ", "").replace("-", "");
        System.out.print("号码：" + number);

        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-3,5-9])|(17[0-9]))\\d{8}$");
        Matcher m = p.matcher(number);

        return m.matches();
    }

    /**
     * 邮件格式
     *
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email) {
        Pattern p = Pattern
                .compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        Matcher m = p.matcher(email);
        System.out.println(m.matches() + "-email-");
        return m.matches();
    }

    /**
     * 用户名匹配
     *
     * @param name
     * @return
     */
    public static boolean isCorrectUserName(String name) {
        Pattern p = Pattern.compile("([A-Za-z0-9]){2,10}");
        Matcher m = p.matcher(name);
        System.out.println(m.matches() + "-name-");
        return m.matches();
    }

    /**
     * 密码匹配，以字母开头，长度 在6-18之间，只能包含字符、数字和下划线。
     *
     * @param pwd
     * @return
     *
     */
    public static boolean isCorrectUserPwd(String pwd) {
        Pattern p = Pattern.compile("\\w{6,18}");
        Matcher m = p.matcher(pwd);
        System.out.println(m.matches() + "-pwd-");
        return m.matches();
    }

    public static void Unzip(String zipFile, String targetDir) {
        Exception ex;
        Exception cwj;
        try {
            FileInputStream fis = new FileInputStream(zipFile);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            BufferedOutputStream dest = null;
            while (true) {
                BufferedOutputStream dest2;
                try {
                    ZipEntry entry = zis.getNextEntry();
                    if (entry == null) {
                        zis.close();
                        fis.close();
                        return;
                    }
                    try {
                        byte[] data = new byte[4096];
                        String strEntry = entry.getName();
                        if (strEntry.contains("/")) {
                            File temp = new File(new StringBuilder(String.valueOf(targetDir)).append(File.separator).append(strEntry.substring(0, strEntry.lastIndexOf("/"))).toString());
                            if (!temp.exists()) {
                                temp.mkdir();
                            }
                        }
                        File entryFile = new File(new StringBuilder(String.valueOf(targetDir)).append(File.separator).append(strEntry).toString());
                        File entryDir = new File(entryFile.getParent());
                        if (!entryDir.exists()) {
                            entryDir.mkdir();
                        }
                        if (entryFile != null && entryFile.getPath().contains(".")) {
                            FileOutputStream fos = new FileOutputStream(entryFile);
                            dest2 = new BufferedOutputStream(fos, 4096);
                            while (true) {
                                try {
                                    int count = zis.read(data, 0, 4096);
                                    if (count == -1) {
                                        break;
                                    }
                                    dest2.write(data, 0, count);
                                } catch (Exception e) {
                                    ex = e;
                                }
                            }
                            dest2.flush();
                            dest2.close();
                            fos.close();
                            dest = dest2;
                        } else if (!entryFile.exists()) {
                            entryFile.mkdir();
                        }
                    } catch (Exception e2) {
                        ex = e2;
                        dest2 = dest;
                        ex.printStackTrace();
                        dest = dest2;
                    }
                } catch (Exception e3) {
                    cwj = e3;
                    dest2 = dest;
                }
            }
        } catch (Exception e4) {
            cwj = e4;
        }
        cwj.printStackTrace();
    }

    public static String getFileNameFromAddress(String address) {
        String fileName = null;
        if (address == null) {
            return null;
        }
        int index = address.lastIndexOf("/");
        if (index != -1) {
            fileName = address.substring(index + 1);
        }
        return fileName;
    }

    public static boolean isNullOrEmpty(String text) {
        if (text == null || "".equals(text.trim()) || text.trim().length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean checkPhone(String phone) {
        if (phone == null || "".equals(phone) || !Pattern.compile("1[0-9]{10}").matcher(phone).matches()) {
            return false;
        }
        return true;
    }

    public static boolean checkEmail(String email) {
        if (email == null || "".equals(email) || !Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*").matcher(email).matches()) {
            return false;
        }
        return true;
    }

    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static int getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public static void callPhone(Context mContext, String phone, boolean isDialView) {
        String action = "android.intent.action.CALL";
        if (isDialView) {
            action = "android.intent.action.DIAL";
        }
        if (!isNullOrEmpty(phone)) {
            Intent intent = new Intent(action, Uri.parse("tel:" + phone));
            intent.setAction(Intent.ACTION_CALL);
            mContext.startActivity(intent);
        }
    }
}
