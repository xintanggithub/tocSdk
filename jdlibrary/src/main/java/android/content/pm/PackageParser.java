//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package android.content.pm;

import android.content.res.TypedArray;
import android.util.DisplayMetrics;

import com.android.internal.util.ArrayUtils;

import java.io.File;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PackageParser {
    private static final boolean DEBUG_JAR = false;
    private static final boolean DEBUG_PARSER = false;
    private static final boolean DEBUG_BACKUP = false;
    private static final String PROPERTY_CHILD_PACKAGES_ENABLED = "persist.sys.child_packages_enabled";
    private static final int MAX_PACKAGES_PER_APK = 5;
    public static final int APK_SIGNING_UNKNOWN = 0;
    public static final int APK_SIGNING_V1 = 1;
    public static final int APK_SIGNING_V2 = 2;
    private static final float DEFAULT_PRE_O_MAX_ASPECT_RATIO = 1.86F;
    private static final String ANDROID_MANIFEST_FILENAME = "AndroidManifest.xml";
    private static final String MNT_EXPAND = "/mnt/expand/";
    private static final String TAG_MANIFEST = "manifest";
    private static final String TAG_APPLICATION = "application";
    private static final String TAG_PACKAGE_VERIFIER = "package-verifier";
    private static final String TAG_OVERLAY = "overlay";
    private static final String TAG_KEY_SETS = "key-sets";
    private static final String TAG_PERMISSION_GROUP = "permission-group";
    private static final String TAG_PERMISSION = "permission";
    private static final String TAG_PERMISSION_TREE = "permission-tree";
    private static final String TAG_USES_PERMISSION = "uses-permission";
    private static final String TAG_USES_PERMISSION_SDK_M = "uses-permission-sdk-m";
    private static final String TAG_USES_PERMISSION_SDK_23 = "uses-permission-sdk-23";
    private static final String TAG_USES_CONFIGURATION = "uses-configuration";
    private static final String TAG_USES_FEATURE = "uses-feature";
    private static final String TAG_FEATURE_GROUP = "feature-group";
    private static final String TAG_USES_SDK = "uses-sdk";
    private static final String TAG_SUPPORT_SCREENS = "supports-screens";
    private static final String TAG_PROTECTED_BROADCAST = "protected-broadcast";
    private static final String TAG_INSTRUMENTATION = "instrumentation";
    private static final String TAG_ORIGINAL_PACKAGE = "original-package";
    private static final String TAG_ADOPT_PERMISSIONS = "adopt-permissions";
    private static final String TAG_USES_GL_TEXTURE = "uses-gl-texture";
    private static final String TAG_COMPATIBLE_SCREENS = "compatible-screens";
    private static final String TAG_SUPPORTS_INPUT = "supports-input";
    private static final String TAG_EAT_COMMENT = "eat-comment";
    private static final String TAG_PACKAGE = "package";
    private static final String TAG_RESTRICT_UPDATE = "restrict-update";
    private static final String TAG_USES_SPLIT = "uses-split";
    private static final String META_DATA_INSTANT_APPS = "instantapps.clients.allowed";
    private static final String METADATA_MAX_ASPECT_RATIO = "android.max_aspect";
    private static final int RECREATE_ON_CONFIG_CHANGES_MASK = 3;
    private static final boolean LOG_UNSAFE_BROADCASTS = false;
    /**
     * @deprecated
     */
    @Deprecated
    private String mArchiveSourcePath;
    private String[] mSeparateProcesses;
    private boolean mOnlyCoreApps;
    private DisplayMetrics mMetrics = new DisplayMetrics();
    private File mCacheDir;
    private int mParseError = 1;
    private static boolean sCompatibilityModeEnabled;
    private static final int PARSE_DEFAULT_INSTALL_LOCATION = -1;
    private static final int PARSE_DEFAULT_TARGET_SANDBOX = 1;
    private PackageParser.ParsePackageItemArgs mParseInstrumentationArgs;
    private PackageParser.ParseComponentArgs mParseActivityArgs;
    private PackageParser.ParseComponentArgs mParseActivityAliasArgs;
    private PackageParser.ParseComponentArgs mParseServiceArgs;
    private PackageParser.ParseComponentArgs mParseProviderArgs;
    private static final boolean RIGID_PARSER = false;
    private static final String TAG = "PackageParser";
    public static final int PARSE_IS_SYSTEM = 1;
    public static final int PARSE_CHATTY = 2;
    public static final int PARSE_MUST_BE_APK = 4;
    public static final int PARSE_IGNORE_PROCESSES = 8;
    public static final int PARSE_FORWARD_LOCK = 16;
    public static final int PARSE_EXTERNAL_STORAGE = 32;
    public static final int PARSE_IS_SYSTEM_DIR = 64;
    public static final int PARSE_IS_PRIVILEGED = 128;
    public static final int PARSE_COLLECT_CERTIFICATES = 256;
    public static final int PARSE_TRUSTED_OVERLAY = 512;
    public static final int PARSE_ENFORCE_CODE = 1024;
    /**
     * @deprecated
     */
    @Deprecated
    public static final int PARSE_IS_EPHEMERAL = 2048;
    public static final int PARSE_FORCE_SDK = 4096;
    private static final String ANDROID_RESOURCES = "http://schemas.android.com/apk/res/android";
    private static AtomicReference<byte[]> sBuffer;

    public PackageParser() {
        this.mMetrics.setToDefaults();
    }

    public void setSeparateProcesses(String[] procs) {
        this.mSeparateProcesses = procs;
    }

    public void setOnlyCoreApps(boolean onlyCoreApps) {
        this.mOnlyCoreApps = onlyCoreApps;
    }

    public void setDisplayMetrics(DisplayMetrics metrics) {
        this.mMetrics = metrics;
    }

    public void setCacheDir(File cacheDir) {
        this.mCacheDir = cacheDir;
    }


    public static final boolean isApkFile(File file) {
        return isApkPath(file.getName());
    }

    public static boolean isApkPath(String path) {
        return path.endsWith(".apk");
    }

    public static class ApkLite {
        public final String codePath;
        public final String packageName;
        public final String splitName;
        public boolean isFeatureSplit;
        public final String configForSplit;
        public final String usesSplitName;
        public final int versionCode;
        public final int revisionCode;
        public final int installLocation;
        public final VerifierInfo[] verifiers;
        public final Signature[] signatures;
        public final Certificate[][] certificates;
        public final boolean coreApp;
        public final boolean debuggable;
        public final boolean multiArch;
        public final boolean use32bitAbi;
        public final boolean extractNativeLibs;
        public final boolean isolatedSplits;

        public ApkLite(String codePath, String packageName, String splitName, boolean isFeatureSplit, String configForSplit, String usesSplitName, int versionCode, int revisionCode, int installLocation, List<VerifierInfo> verifiers, Signature[] signatures, Certificate[][] certificates, boolean coreApp, boolean debuggable, boolean multiArch, boolean use32bitAbi, boolean extractNativeLibs, boolean isolatedSplits) {
            this.codePath = codePath;
            this.packageName = packageName;
            this.splitName = splitName;
            this.isFeatureSplit = isFeatureSplit;
            this.configForSplit = configForSplit;
            this.usesSplitName = usesSplitName;
            this.versionCode = versionCode;
            this.revisionCode = revisionCode;
            this.installLocation = installLocation;
            this.verifiers = (VerifierInfo[]) verifiers.toArray(new VerifierInfo[verifiers.size()]);
            this.signatures = signatures;
            this.certificates = certificates;
            this.coreApp = coreApp;
            this.debuggable = debuggable;
            this.multiArch = multiArch;
            this.use32bitAbi = use32bitAbi;
            this.extractNativeLibs = extractNativeLibs;
            this.isolatedSplits = isolatedSplits;
        }
    }

    public static class PackageLite {
        public final String packageName;
        public final int versionCode;
        public final int installLocation;
        public final VerifierInfo[] verifiers;
        public final String[] splitNames;
        public final boolean[] isFeatureSplits;
        public final String[] usesSplitNames;
        public final String[] configForSplit;
        public final String codePath;
        public final String baseCodePath;
        public final String[] splitCodePaths;
        public final int baseRevisionCode;
        public final int[] splitRevisionCodes;
        public final boolean coreApp;
        public final boolean debuggable;
        public final boolean multiArch;
        public final boolean use32bitAbi;
        public final boolean extractNativeLibs;
        public final boolean isolatedSplits;

        public PackageLite(String codePath, PackageParser.ApkLite baseApk, String[] splitNames, boolean[] isFeatureSplits, String[] usesSplitNames, String[] configForSplit, String[] splitCodePaths, int[] splitRevisionCodes) {
            this.packageName = baseApk.packageName;
            this.versionCode = baseApk.versionCode;
            this.installLocation = baseApk.installLocation;
            this.verifiers = baseApk.verifiers;
            this.splitNames = splitNames;
            this.isFeatureSplits = isFeatureSplits;
            this.usesSplitNames = usesSplitNames;
            this.configForSplit = configForSplit;
            this.codePath = codePath;
            this.baseCodePath = baseApk.codePath;
            this.splitCodePaths = splitCodePaths;
            this.baseRevisionCode = baseApk.revisionCode;
            this.splitRevisionCodes = splitRevisionCodes;
            this.coreApp = baseApk.coreApp;
            this.debuggable = baseApk.debuggable;
            this.multiArch = baseApk.multiArch;
            this.use32bitAbi = baseApk.use32bitAbi;
            this.extractNativeLibs = baseApk.extractNativeLibs;
            this.isolatedSplits = baseApk.isolatedSplits;
        }

        public List<String> getAllCodePaths() {
            ArrayList<String> paths = new ArrayList();
            paths.add(this.baseCodePath);
            if (!ArrayUtils.isEmpty(this.splitCodePaths)) {
                Collections.addAll(paths, this.splitCodePaths);
            }

            return paths;
        }
    }

    public static class ParseComponentArgs extends PackageParser.ParsePackageItemArgs {
        final String[] sepProcesses;
        final int processRes;
        final int descriptionRes;
        final int enabledRes;
        int flags;

        public ParseComponentArgs(String[] _outError, int _nameRes, int _labelRes, int _iconRes, int _roundIconRes, int _logoRes, int _bannerRes, String[] _sepProcesses, int _processRes, int _descriptionRes, int _enabledRes) {
            super(_outError, _nameRes, _labelRes, _iconRes, _roundIconRes, _logoRes, _bannerRes);
            this.sepProcesses = _sepProcesses;
            this.processRes = _processRes;
            this.descriptionRes = _descriptionRes;
            this.enabledRes = _enabledRes;
        }
    }

    static class ParsePackageItemArgs {
        final String[] outError;
        final int nameRes;
        final int labelRes;
        final int iconRes;
        final int roundIconRes;
        final int logoRes;
        final int bannerRes;
        String tag;
        TypedArray sa;

        ParsePackageItemArgs(String[] _outError, int _nameRes, int _labelRes, int _iconRes, int _roundIconRes, int _logoRes, int _bannerRes) {
            this.outError = _outError;
            this.nameRes = _nameRes;
            this.labelRes = _labelRes;
            this.iconRes = _iconRes;
            this.logoRes = _logoRes;
            this.bannerRes = _bannerRes;
            this.roundIconRes = _roundIconRes;
        }
    }

    public static class SplitPermissionInfo {
        public final String rootPerm;
        public final String[] newPerms;
        public final int targetSdk;

        public SplitPermissionInfo(String rootPerm, String[] newPerms, int targetSdk) {
            this.rootPerm = rootPerm;
            this.newPerms = newPerms;
            this.targetSdk = targetSdk;
        }
    }

    public static class NewPermissionInfo {
        public final String name;
        public final int sdkVersion;
        public final int fileVersion;

        public NewPermissionInfo(String name, int sdkVersion, int fileVersion) {
            this.name = name;
            this.sdkVersion = sdkVersion;
            this.fileVersion = fileVersion;
        }
    }

    public static class PackageParserException extends Exception {
        public final int error;

        public PackageParserException(int error, String detailMessage) {
            super(detailMessage);
            this.error = error;
        }

        public PackageParserException(int error, String detailMessage, Throwable throwable) {
            super(detailMessage, throwable);
            this.error = error;
        }
    }

    public static PackageParser.ApkLite parseApkLite(File f, int p) {

        return null;
    }
}
