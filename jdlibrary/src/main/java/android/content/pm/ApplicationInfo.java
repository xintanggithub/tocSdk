/*
 * Copyright (C) 2007 The Android Open Source Project
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

package android.content.pm;

import android.annotation.IntDef;
import android.annotation.Nullable;
import android.annotation.SystemApi;
import android.annotation.TestApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.storage.StorageManager;
import android.util.SparseArray;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.UUID;

/**
 * Information you can retrieve about a particular application.  This
 * corresponds to information collected from the AndroidManifest.xml's
 * &lt;application&gt; tag.
 */
public class ApplicationInfo extends PackageItemInfo implements Parcelable {

    /**
     * Default task affinity of all activities in this application. See
     * {@link ActivityInfo#taskAffinity} for more information.  This comes
     * from the "taskAffinity" attribute.
     */
    public String taskAffinity;

    /**
     * Optional name of a permission required to be able to access this
     * application's components.  From the "permission" attribute.
     */
    public String permission;

    /**
     * The name of the process this application should run in.  From the
     * "process" attribute or, if not set, the same as
     * <var>packageName</var>.
     */
    public String processName;

    /**
     * Class implementing the Application object.  From the "class"
     * attribute.
     */
    public String className;

    /**
     * A style resource identifier (in the package's resources) of the
     * description of an application.  From the "description" attribute
     * or, if not set, 0.
     */
    public int descriptionRes;

    /**
     * A style resource identifier (in the package's resources) of the
     * default visual theme of the application.  From the "theme" attribute
     * or, if not set, 0.
     */
    public int theme;

    /**
     * Class implementing the Application's manage space
     * functionality.  From the "manageSpaceActivity"
     * attribute. This is an optional attribute and will be null if
     * applications don't specify it in their manifest
     */
    public String manageSpaceActivityName;

    /**
     * Class implementing the Application's backup functionality.  From
     * the "backupAgent" attribute.  This is an optional attribute and
     * will be null if the application does not specify it in its manifest.
     *
     * <p>If android:allowBackup is set to false, this attribute is ignored.
     */
    public String backupAgentName;

    /**
     * The default extra UI options for activities in this application.
     * Set from the {@link android.R.attr#uiOptions} attribute in the
     * activity's manifest.
     */
    public int uiOptions = 0;

    /**
     * Value for {@link #flags}: if set, this application is installed in the
     * device's system image.
     */
    public static final int FLAG_SYSTEM = 1 << 0;


    /**
     * Value for {@link #flags}: this is set if this application has been
     * installed as an update to a built-in system application.
     */
    public static final int FLAG_UPDATED_SYSTEM_APP = 1 << 7;


    /**
     * Value for {@link #flags}: true if this application's package is in
     * the stopped state.
     */
    public static final int FLAG_STOPPED = 1 << 21;

    /**
     * Value for {@link #flags}: true  when the application is willing to support
     * RTL (right to left). All activities will inherit this value.
     * <p>
     * Set from the {@link android.R.attr#supportsRtl} attribute in the
     * activity's manifest.
     * <p>
     * Default value is false (no support for RTL).
     */
    public static final int FLAG_SUPPORTS_RTL = 1 << 22;

    /**
     * Value for {@link #flags}: true if the application is currently
     * installed for the calling user.
     */
    public static final int FLAG_INSTALLED = 1 << 23;

    /**
     * Value for {@link #flags}: true if the application only has its
     * data installed; the application package itself does not currently
     * exist on the device.
     */
    public static final int FLAG_IS_DATA_ONLY = 1 << 24;

    /**
     * Value for {@link #flags}: true if the application was declared to be a
     * game, or false if it is a non-game application.
     *
     * @deprecated use {@link #CATEGORY_GAME} instead.
     */
    @Deprecated
    public static final int FLAG_IS_GAME = 1 << 25;

    /**
     * Value for {@link #flags}: {@code true} if the application asks that only
     * full-data streaming backups of its data be performed even though it defines
     * a {@link android.app.backup.BackupAgent BackupAgent}, which normally
     * indicates that the app will manage its backed-up data via incremental
     * key/value updates.
     */
    public static final int FLAG_FULL_BACKUP_ONLY = 1 << 26;

    /**
     * Value for {@link #flags}: {@code true} if the application may use cleartext network traffic
     * (e.g., HTTP rather than HTTPS; WebSockets rather than WebSockets Secure; XMPP, IMAP, STMP
     * without STARTTLS or TLS). If {@code false}, the app declares that it does not intend to use
     * cleartext network traffic, in which case platform components (e.g., HTTP stacks,
     * {@code DownloadManager}, {@code MediaPlayer}) will refuse app's requests to use cleartext
     * traffic. Third-party libraries are encouraged to honor this flag as well.
     *
     * <p>NOTE: {@code WebView} honors this flag for applications targeting API level 26 and up.
     *
     * <p>This flag is ignored on Android N and above if an Android Network Security Config is
     * present.
     *
     * <p>This flag comes from
     * android:usesCleartextTraffic} of the &lt;application&gt; tag.
     */
    public static final int FLAG_USES_CLEARTEXT_TRAFFIC = 1 << 27;

    /**
     * When set installer extracts native libs from .apk files.
     */
    public static final int FLAG_EXTRACT_NATIVE_LIBS = 1 << 28;

    /**
     * Value for {@link #flags}: {@code true} when the application's rendering
     * should be hardware accelerated.
     */
    public static final int FLAG_HARDWARE_ACCELERATED = 1 << 29;

    /**
     * Value for {@link #flags}: true if this application's package is in
     * the suspended state.
     */
    public static final int FLAG_SUSPENDED = 1 << 30;

    /**
     * Value for {@link #flags}: true if code from this application will need to be
     * loaded into other applications' processes. On devices that support multiple
     * instruction sets, this implies the code might be loaded into a process that's
     * using any of the devices supported instruction sets.
     *
     * <p> The system might treat such applications specially, for eg., by
     * extracting the application's native libraries for all supported instruction
     * sets or by compiling the application's dex code for all supported instruction
     * sets.
     */
    public static final int FLAG_MULTIARCH = 1 << 31;

    public int flags = 0;

    /**
     * Value for {@link #privateFlags}: true if the application is hidden via restrictions and for
     * most purposes is considered as not installed.
     * {@hide}
     */
    public static final int PRIVATE_FLAG_HIDDEN = 1 << 0;

    /**
     * Value for {@link #privateFlags}: set to <code>true</code> if the application
     * has reported that it is heavy-weight, and thus can not participate in
     * the normal application lifecycle.
     *
     * <p>Comes from the
     * android.R.styleable#AndroidManifestApplication_cantSaveState
     * attribute of the &lt;application&gt; tag.
     * <p>
     * {@hide}
     */
    public static final int PRIVATE_FLAG_CANT_SAVE_STATE = 1 << 1;

    /**
     * Value for {@link #privateFlags}: Set to true if the application has been
     * installed using the forward lock option.
     * <p>
     * NOTE: DO NOT CHANGE THIS VALUE!  It is saved in packages.xml.
     * <p>
     * {@hide}
     */
    public static final int PRIVATE_FLAG_FORWARD_LOCK = 1 << 2;

    /**
     * Value for {@link #privateFlags}: set to {@code true} if the application
     * is permitted to hold privileged permissions.
     * <p>
     * {@hide}
     */
    public static final int PRIVATE_FLAG_PRIVILEGED = 1 << 3;

    /**
     * Value for {@link #privateFlags}: {@code true} if the application has any IntentFiler
     * with some data URI using HTTP or HTTPS with an associated VIEW action.
     * <p>
     * {@hide}
     */
    public static final int PRIVATE_FLAG_HAS_DOMAIN_URLS = 1 << 4;

    /**
     * When set, the default data storage directory for this app is pointed at
     * the device-protected location.
     *
     * @hide
     */
    public static final int PRIVATE_FLAG_DEFAULT_TO_DEVICE_PROTECTED_STORAGE = 1 << 5;

    /**
     * When set, assume that all components under the given app are direct boot
     * aware, unless otherwise specified.
     *
     * @hide
     */
    public static final int PRIVATE_FLAG_DIRECT_BOOT_AWARE = 1 << 6;

    /**
     * Value for {@link #privateFlags}: {@code true} if the application is installed
     * as instant app.
     *
     * @hide
     */
    public static final int PRIVATE_FLAG_INSTANT = 1 << 7;

    /**
     * When set, at least one component inside this application is direct boot
     * aware.
     *
     * @hide
     */
    public static final int PRIVATE_FLAG_PARTIALLY_DIRECT_BOOT_AWARE = 1 << 8;


    /**
     * When set, signals that the application is required for the system user and should not be
     * uninstalled.
     *
     * @hide
     */
    public static final int PRIVATE_FLAG_REQUIRED_FOR_SYSTEM_USER = 1 << 9;

    /**
     * @hide
     */
    public static final int PRIVATE_FLAG_ACTIVITIES_RESIZE_MODE_RESIZEABLE = 1 << 10;

    /**
     * When set, the application explicitly requested that its activities *not* be resizeable by
     * default.
     *
     * @hide
     */
    public static final int PRIVATE_FLAG_ACTIVITIES_RESIZE_MODE_UNRESIZEABLE = 1 << 11;

    /**
     * @hide
     */
    public static final int PRIVATE_FLAG_ACTIVITIES_RESIZE_MODE_RESIZEABLE_VIA_SDK_VERSION =
            1 << 12;

    /**
     * Value for {@link #privateFlags}: {@code true} means the OS should go ahead and
     * run full-data backup operations for the app even when it is in a
     * foreground-equivalent run state.  Defaults to {@code false} if unspecified.
     *
     * @hide
     */
    public static final int PRIVATE_FLAG_BACKUP_IN_FOREGROUND = 1 << 13;

    /**
     * Value for {@link #privateFlags}: {@code true} means this application
     * contains a static shared library. Defaults to {@code false} if unspecified.
     *
     * @hide
     */
    public static final int PRIVATE_FLAG_STATIC_SHARED_LIBRARY = 1 << 14;

    /**
     * Value for {@link #privateFlags}: When set, the application will only have its splits loaded
     * if they are required to load a component. Splits can be loaded on demand using the
     * {@link Context#createContextForSplit(String)} API.
     *
     * @hide
     */
    public static final int PRIVATE_FLAG_ISOLATED_SPLIT_LOADING = 1 << 15;

    /**
     * Value for {@link #privateFlags}: When set, the application was installed as
     * a virtual preload.
     *
     * @hide
     */
    public static final int PRIVATE_FLAG_VIRTUAL_PRELOAD = 1 << 16;

    /**
     * Value for {@link #privateFlags}: whether this app is pre-installed on the
     * OEM partition of the system image.
     *
     * @hide
     */
    public static final int PRIVATE_FLAG_OEM = 1 << 17;

    /**
     * Value for {@link #privateFlags}: whether this app is pre-installed on the
     * vendor partition of the system image.
     *
     * @hide
     */
    public static final int PRIVATE_FLAG_VENDOR = 1 << 18;

    /**
     * Value for {@link #privateFlags}: whether this app is pre-installed on the
     * product partition of the system image.
     *
     * @hide
     */
    public static final int PRIVATE_FLAG_PRODUCT = 1 << 19;

    /**
     * Value for {@link #privateFlags}: whether this app is signed with the
     * platform key.
     *
     * @hide
     */
    public static final int PRIVATE_FLAG_SIGNED_WITH_PLATFORM_KEY = 1 << 20;

    /**
     * @hide
     */
    @IntDef(flag = true, prefix = {"PRIVATE_FLAG_"}, value = {
            PRIVATE_FLAG_ACTIVITIES_RESIZE_MODE_RESIZEABLE,
            PRIVATE_FLAG_ACTIVITIES_RESIZE_MODE_RESIZEABLE_VIA_SDK_VERSION,
            PRIVATE_FLAG_ACTIVITIES_RESIZE_MODE_UNRESIZEABLE,
            PRIVATE_FLAG_BACKUP_IN_FOREGROUND,
            PRIVATE_FLAG_CANT_SAVE_STATE,
            PRIVATE_FLAG_DEFAULT_TO_DEVICE_PROTECTED_STORAGE,
            PRIVATE_FLAG_DIRECT_BOOT_AWARE,
            PRIVATE_FLAG_FORWARD_LOCK,
            PRIVATE_FLAG_HAS_DOMAIN_URLS,
            PRIVATE_FLAG_HIDDEN,
            PRIVATE_FLAG_INSTANT,
            PRIVATE_FLAG_ISOLATED_SPLIT_LOADING,
            PRIVATE_FLAG_OEM,
            PRIVATE_FLAG_PARTIALLY_DIRECT_BOOT_AWARE,
            PRIVATE_FLAG_PRIVILEGED,
            PRIVATE_FLAG_PRODUCT,
            PRIVATE_FLAG_REQUIRED_FOR_SYSTEM_USER,
            PRIVATE_FLAG_SIGNED_WITH_PLATFORM_KEY,
            PRIVATE_FLAG_STATIC_SHARED_LIBRARY,
            PRIVATE_FLAG_VENDOR,
            PRIVATE_FLAG_VIRTUAL_PRELOAD,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ApplicationInfoPrivateFlags {
    }

    /**
     * Private/hidden flags. See {@code PRIVATE_FLAG_...} constants.
     *
     * @hide
     */
    public @ApplicationInfoPrivateFlags
    int privateFlags;

    /**
     * @hide
     */
    public static final String METADATA_PRELOADED_FONTS = "preloaded_fonts";

    /**
     * Value indicating the maximum aspect ratio the application supports.
     * <p>
     * 0 means unset.
     *
     * @See {@link android.R.attr#maxAspectRatio}.
     * @hide
     */
    public float maxAspectRatio;

    /**
     * @removed
     */
    @Deprecated
    public String volumeUuid;

    /**
     * UUID of the storage volume on which this application is being hosted. For
     * apps hosted on the default internal storage at
     * {@link Environment#getDataDirectory()}, the UUID value is
     * {@link StorageManager#UUID_DEFAULT}.
     */
    public UUID storageUuid;

    /**
     * {@hide}
     */
    public String scanSourceDir;
    /**
     * {@hide}
     */
    public String scanPublicSourceDir;

    /**
     * Full path to the base APK for this application.
     */
    public String sourceDir;

    /**
     * Full path to the publicly available parts of {@link #sourceDir},
     * including resources and manifest. This may be different from
     * {@link #sourceDir} if an application is forward locked.
     */
    public String publicSourceDir;

    /**
     * The names of all installed split APKs, ordered lexicographically.
     */
    public String[] splitNames;

    /**
     * Full paths to zero or more split APKs, indexed by the same order as {@link #splitNames}.
     */
    public String[] splitSourceDirs;

    /**
     * Full path to the publicly available parts of {@link #splitSourceDirs},
     * including resources and manifest. This may be different from
     * {@link #splitSourceDirs} if an application is forward locked.
     *
     * @see #splitSourceDirs
     */
    public String[] splitPublicSourceDirs;

    /**
     * Maps the dependencies between split APKs. All splits implicitly depend on the base APK.
     * <p>
     * Available since platform version O.
     * <p>
     * Only populated if the application opts in to isolated split loading via the
     * AndroidManifest.xml.
     * <p>
     * The keys and values are all indices into the {@link #splitNames}, {@link #splitSourceDirs},
     * and {@link #splitPublicSourceDirs} arrays.
     * Each key represents a split and its value is an array of splits. The first element of this
     * array is the parent split, and the rest are configuration splits. These configuration splits
     * have no dependencies themselves.
     * Cycles do not exist because they are illegal and screened for during installation.
     * <p>
     * May be null if no splits are installed, or if no dependencies exist between them.
     * <p>
     * NOTE: Any change to the way split dependencies are stored must update the logic that
     * creates the class loader context for dexopt (DexoptUtils#getClassLoaderContexts).
     *
     * @hide
     */
    public SparseArray<int[]> splitDependencies;

    /**
     * Full paths to the locations of extra resource packages (runtime overlays)
     * this application uses. This field is only used if there are extra resource
     * packages, otherwise it is null.
     * <p>
     * {@hide}
     */
    public String[] resourceDirs;

    /**
     * String retrieved from the seinfo tag found in selinux policy. This value can be set through
     * the mac_permissions.xml policy construct. This value is used for setting an SELinux security
     * context on the process as well as its data directory.
     * <p>
     * {@hide}
     */
    public String seInfo;

    /**
     * The seinfo tag generated per-user. This value may change based upon the
     * user's configuration. For example, when an instant app is installed for
     * a user. It is an error if this field is ever {@code null} when trying to
     * start a new process.
     * <p>NOTE: We need to separate this out because we modify per-user values
     * multiple times. This needs to be refactored since we're performing more
     * work than necessary and these values should only be set once. When that
     * happens, we can merge the per-user value with the seInfo state above.
     * <p>
     * {@hide}
     */
    public String seInfoUser;

    /**
     * Paths to all shared libraries this application is linked against.  This
     * field is only set if the {@link PackageManager#GET_SHARED_LIBRARY_FILES
     * PackageManager.GET_SHARED_LIBRARY_FILES} flag was used when retrieving
     * the structure.
     */
    public String[] sharedLibraryFiles;

    /**
     * Full path to the default directory assigned to the package for its
     * persistent data.
     */
    public String dataDir;

    /**
     * Full path to the device-protected directory assigned to the package for
     * its persistent data.
     *
     * @see Context#createDeviceProtectedStorageContext()
     */
    public String deviceProtectedDataDir;

    /**
     * Full path to the credential-protected directory assigned to the package
     * for its persistent data.
     *
     * @hide
     */
    @SystemApi
    public String credentialProtectedDataDir;

    /**
     * Full path to the directory where native JNI libraries are stored.
     */
    public String nativeLibraryDir;

    /**
     * Full path where unpacked native libraries for {@link #secondaryCpuAbi}
     * are stored, if present.
     * <p>
     * The main reason this exists is for bundled multi-arch apps, where
     * it's not trivial to calculate the location of libs for the secondary abi
     * given the location of the primary.
     * <p>
     * TODO: Change the layout of bundled installs so that we can use
     * nativeLibraryRootDir & nativeLibraryRootRequiresIsa there as well.
     * (e.g {@code [ "/system/app-lib/Foo/arm", "/system/app-lib/Foo/arm64" ]}
     * instead of {@code [ "/system/lib/Foo", "/system/lib64/Foo" ]}.
     *
     * @hide
     */
    public String secondaryNativeLibraryDir;

    /**
     * The root path where unpacked native libraries are stored.
     * <p>
     * When {@link #nativeLibraryRootRequiresIsa} is set, the libraries are
     * placed in ISA-specific subdirectories under this path, otherwise the
     * libraries are placed directly at this path.
     *
     * @hide
     */
    public String nativeLibraryRootDir;

    /**
     * Flag indicating that ISA must be appended to
     * {@link #nativeLibraryRootDir} to be useful.
     *
     * @hide
     */
    public boolean nativeLibraryRootRequiresIsa;

    /**
     * The primary ABI that this application requires, This is inferred from the ABIs
     * of the native JNI libraries the application bundles. Will be {@code null}
     * if this application does not require any particular ABI.
     * <p>
     * If non-null, the application will always be launched with this ABI.
     * <p>
     * {@hide}
     */
    public String primaryCpuAbi;

    /**
     * The secondary ABI for this application. Might be non-null for multi-arch
     * installs. The application itself never uses this ABI, but other applications that
     * use its code might.
     * <p>
     * {@hide}
     */
    public String secondaryCpuAbi;

    /**
     * The kernel user-ID that has been assigned to this application;
     * currently this is not a unique ID (multiple applications can have
     * the same uid).
     */
    public int uid;

    /**
     * The minimum SDK version this application can run on. It will not run
     * on earlier versions.
     */
    public int minSdkVersion;

    /**
     * The minimum SDK version this application targets.  It may run on earlier
     * versions, but it knows how to work with any new behavior added at this
     * version.  Will be {@link android.os.Build.VERSION_CODES#CUR_DEVELOPMENT}
     * if this is a development build and the app is targeting that.  You should
     * compare that this number is >= the SDK version number at which your
     * behavior was introduced.
     */
    public int targetSdkVersion;

    /**
     * The app's declared version code.
     *
     * @hide
     */
    public long longVersionCode;

    /**
     * An integer representation of the app's declared version code. This is being left in place as
     * some apps were using reflection to access it before the move to long in
     * {@link android.os.Build.VERSION_CODES#P}
     *
     * @hide
     * @deprecated Use {@link #longVersionCode} instead.
     */
    @Deprecated
    public int versionCode;

    /**
     * The user-visible SDK version (ex. 26) of the framework against which the application claims
     * to have been compiled, or {@code 0} if not specified.
     * <p>
     * This property is the compile-time equivalent of
     * {@link android.os.Build.VERSION#CODENAME Build.VERSION.SDK_INT}.
     *
     * @hide For platform use only; we don't expect developers to need to read this value.
     */
    public int compileSdkVersion;

    /**
     * The development codename (ex. "O", "REL") of the framework against which the application
     * claims to have been compiled, or {@code null} if not specified.
     * <p>
     * This property is the compile-time equivalent of
     * {@link android.os.Build.VERSION#CODENAME Build.VERSION.CODENAME}.
     *
     * @hide For platform use only; we don't expect developers to need to read this value.
     */
    @Nullable
    public String compileSdkVersionCodename;

    /**
     * When false, indicates that all components within this application are
     * considered disabled, regardless of their individually set enabled status.
     */
    public boolean enabled = true;

    /**
     * For convenient access to the current enabled setting of this app.
     *
     * @hide
     */
    public int enabledSetting = PackageManager.COMPONENT_ENABLED_STATE_DEFAULT;

    /**
     * Resource file providing the application's Network Security Config.
     *
     * @hide
     */
    public int networkSecurityConfigRes;

    /**
     * Version of the sandbox the application wants to run in.
     *
     * @hide
     */
    @SystemApi
    public int targetSandboxVersion;

    public String appComponentFactory;

    /**
     * The category of this app. Categories are used to cluster multiple apps
     * together into meaningful groups, such as when summarizing battery,
     * network, or disk usage. Apps should only define this value when they fit
     * well into one of the specific categories.
     * <p>
     * Set from the {@link android.R.attr#appCategory} attribute in the
     * manifest. If the manifest doesn't define a category, this value may have
     * been provided by the installer via
     * {@link PackageManager#setApplicationCategoryHint(String, int)}.
     */
    public @Category
    int category = CATEGORY_UNDEFINED;

    /**
     * {@hide}
     */
    @IntDef(prefix = {"CATEGORY_"}, value = {
            CATEGORY_UNDEFINED,
            CATEGORY_GAME,
            CATEGORY_AUDIO,
            CATEGORY_VIDEO,
            CATEGORY_IMAGE,
            CATEGORY_SOCIAL,
            CATEGORY_NEWS,
            CATEGORY_MAPS,
            CATEGORY_PRODUCTIVITY
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Category {
    }

    /**
     * Value when category is undefined.
     *
     * @see #category
     */
    public static final int CATEGORY_UNDEFINED = -1;

    /**
     * Category for apps which are primarily games.
     *
     * @see #category
     */
    public static final int CATEGORY_GAME = 0;

    /**
     * Category for apps which primarily work with audio or music, such as music
     * players.
     *
     * @see #category
     */
    public static final int CATEGORY_AUDIO = 1;

    /**
     * Category for apps which primarily work with video or movies, such as
     * streaming video apps.
     *
     * @see #category
     */
    public static final int CATEGORY_VIDEO = 2;

    /**
     * Category for apps which primarily work with images or photos, such as
     * camera or gallery apps.
     *
     * @see #category
     */
    public static final int CATEGORY_IMAGE = 3;

    /**
     * Category for apps which are primarily social apps, such as messaging,
     * communication, email, or social network apps.
     *
     * @see #category
     */
    public static final int CATEGORY_SOCIAL = 4;

    /**
     * Category for apps which are primarily news apps, such as newspapers,
     * magazines, or sports apps.
     *
     * @see #category
     */
    public static final int CATEGORY_NEWS = 5;

    /**
     * Category for apps which are primarily maps apps, such as navigation apps.
     *
     * @see #category
     */
    public static final int CATEGORY_MAPS = 6;

    /**
     * Category for apps which are primarily productivity apps, such as cloud
     * storage or workplace apps.
     *
     * @see #category
     */
    public static final int CATEGORY_PRODUCTIVITY = 7;


    protected ApplicationInfo(Parcel in) {
        taskAffinity = in.readString();
        permission = in.readString();
        processName = in.readString();
        className = in.readString();
        descriptionRes = in.readInt();
        theme = in.readInt();
        manageSpaceActivityName = in.readString();
        backupAgentName = in.readString();
        uiOptions = in.readInt();
        flags = in.readInt();
        privateFlags = in.readInt();
        maxAspectRatio = in.readFloat();
        volumeUuid = in.readString();
        scanSourceDir = in.readString();
        scanPublicSourceDir = in.readString();
        sourceDir = in.readString();
        publicSourceDir = in.readString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            splitNames = in.createStringArray();
            splitSourceDirs = in.createStringArray();
            splitPublicSourceDirs = in.createStringArray();
        }
        resourceDirs = in.createStringArray();
        seInfo = in.readString();
        seInfoUser = in.readString();
        sharedLibraryFiles = in.createStringArray();
        dataDir = in.readString();
        deviceProtectedDataDir = in.readString();
        credentialProtectedDataDir = in.readString();
        nativeLibraryDir = in.readString();
        secondaryNativeLibraryDir = in.readString();
        nativeLibraryRootDir = in.readString();
        nativeLibraryRootRequiresIsa = in.readByte() != 0;
        primaryCpuAbi = in.readString();
        secondaryCpuAbi = in.readString();
        uid = in.readInt();
        minSdkVersion = in.readInt();
        targetSdkVersion = in.readInt();
        longVersionCode = in.readLong();
        versionCode = in.readInt();
        compileSdkVersion = in.readInt();
        compileSdkVersionCodename = in.readString();
        enabled = in.readByte() != 0;
        enabledSetting = in.readInt();
        networkSecurityConfigRes = in.readInt();
        targetSandboxVersion = in.readInt();
        appComponentFactory = in.readString();
        category = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(taskAffinity);
        dest.writeString(permission);
        dest.writeString(processName);
        dest.writeString(className);
        dest.writeInt(descriptionRes);
        dest.writeInt(theme);
        dest.writeString(manageSpaceActivityName);
        dest.writeString(backupAgentName);
        dest.writeInt(uiOptions);
        dest.writeInt(flags);
        dest.writeInt(privateFlags);
        dest.writeFloat(maxAspectRatio);
        dest.writeString(volumeUuid);
        dest.writeString(scanSourceDir);
        dest.writeString(scanPublicSourceDir);
        dest.writeString(sourceDir);
        dest.writeString(publicSourceDir);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dest.writeStringArray(splitNames);
            dest.writeStringArray(splitSourceDirs);
            dest.writeStringArray(splitPublicSourceDirs);
        }
        dest.writeStringArray(resourceDirs);
        dest.writeString(seInfo);
        dest.writeString(seInfoUser);
        dest.writeStringArray(sharedLibraryFiles);
        dest.writeString(dataDir);
        dest.writeString(deviceProtectedDataDir);
        dest.writeString(credentialProtectedDataDir);
        dest.writeString(nativeLibraryDir);
        dest.writeString(secondaryNativeLibraryDir);
        dest.writeString(nativeLibraryRootDir);
        dest.writeByte((byte) (nativeLibraryRootRequiresIsa ? 1 : 0));
        dest.writeString(primaryCpuAbi);
        dest.writeString(secondaryCpuAbi);
        dest.writeInt(uid);
        dest.writeInt(minSdkVersion);
        dest.writeInt(targetSdkVersion);
        dest.writeLong(longVersionCode);
        dest.writeInt(versionCode);
        dest.writeInt(compileSdkVersion);
        dest.writeString(compileSdkVersionCodename);
        dest.writeByte((byte) (enabled ? 1 : 0));
        dest.writeInt(enabledSetting);
        dest.writeInt(networkSecurityConfigRes);
        dest.writeInt(targetSandboxVersion);
        dest.writeString(appComponentFactory);
        dest.writeInt(category);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApplicationInfo> CREATOR = new Creator<ApplicationInfo>() {
        @Override
        public ApplicationInfo createFromParcel(Parcel in) {
            return new ApplicationInfo(in);
        }

        @Override
        public ApplicationInfo[] newArray(int size) {
            return new ApplicationInfo[size];
        }
    };

    /**
     * @hide
     */
    @TestApi
    public boolean isPrivilegedApp() {
        return (privateFlags & ApplicationInfo.PRIVATE_FLAG_PRIVILEGED) != 0;
    }

    /**
     * @hide
     */
    public boolean isRequiredForSystemUser() {
        return (privateFlags & ApplicationInfo.PRIVATE_FLAG_REQUIRED_FOR_SYSTEM_USER) != 0;
    }

    /**
     * @hide
     */
    public boolean isStaticSharedLibrary() {
        return (privateFlags & ApplicationInfo.PRIVATE_FLAG_STATIC_SHARED_LIBRARY) != 0;
    }

    /**
     * @hide
     */
    @TestApi
    public boolean isSystemApp() {
        return (flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }

    /**
     * @hide
     */
    public boolean isUpdatedSystemApp() {
        return (flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0;
    }

    /**
     * @hide
     */
    public boolean isVendor() {
        return (privateFlags & ApplicationInfo.PRIVATE_FLAG_VENDOR) != 0;
    }

    /**
     * @hide
     */
    public boolean isProduct() {
        return (privateFlags & ApplicationInfo.PRIVATE_FLAG_PRODUCT) != 0;
    }

    /**
     * Returns whether or not this application was installed as a virtual preload.
     */
    public boolean isVirtualPreload() {
        return (privateFlags & PRIVATE_FLAG_VIRTUAL_PRELOAD) != 0;
    }

    /**
     * Returns true if the app has declared in its manifest that it wants its split APKs to be
     * loaded into isolated Contexts, with their own ClassLoaders and Resources objects.
     *
     * @hide
     */
    public boolean requestsIsolatedSplitLoading() {
        return (privateFlags & ApplicationInfo.PRIVATE_FLAG_ISOLATED_SPLIT_LOADING) != 0;
    }

}
