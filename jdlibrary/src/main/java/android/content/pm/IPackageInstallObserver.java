//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPackageInstallObserver extends IInterface {
    void packageInstalled(String var1, int var2) throws RemoteException;

    public abstract static class Stub extends Binder implements IPackageInstallObserver {
        private static final String DESCRIPTOR = "android.content.pm.IPackageInstallObserver";
        static final int TRANSACTION_packageInstalled = 1;

        public Stub() {
            this.attachInterface(this, "android.content.pm.IPackageInstallObserver");
        }

        public static IPackageInstallObserver asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            } else {
                IInterface iin = obj.queryLocalInterface("android.content.pm.IPackageInstallObserver");
                return (IPackageInstallObserver) (iin != null && iin instanceof IPackageInstallObserver ? (IPackageInstallObserver) iin : new IPackageInstallObserver.Stub.Proxy(obj));
            }
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case 1:
                    data.enforceInterface("android.content.pm.IPackageInstallObserver");
                    String _arg0 = data.readString();
                    int _arg1 = data.readInt();
                    this.packageInstalled(_arg0, _arg1);
                    return true;
                case 1598968902:
                    reply.writeString("android.content.pm.IPackageInstallObserver");
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IPackageInstallObserver {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return "android.content.pm.IPackageInstallObserver";
            }

            public void packageInstalled(String packageName, int returnCode) throws RemoteException {
                Parcel _data = Parcel.obtain();

                try {
                    _data.writeInterfaceToken("android.content.pm.IPackageInstallObserver");
                    _data.writeString(packageName);
                    _data.writeInt(returnCode);
                    this.mRemote.transact(1, _data, (Parcel) null, 1);
                } finally {
                    _data.recycle();
                }

            }
        }
    }
}
