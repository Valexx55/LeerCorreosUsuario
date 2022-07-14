package edu.val.leercorreosusuario;

import static android.Manifest.permission.ACCOUNT_MANAGER;
import static android.Manifest.permission.GET_ACCOUNTS;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.SEND_SMS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pedirPermisosAccesoACuentas();
    }

    private void pedirPermisosAccesoACuentas()
    {
        String[] array_permisos = {GET_ACCOUNTS, READ_CONTACTS};

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            //si tengo permiso concedido, no lo pido
            //si no, lo pido
            //TRUCO PRÁCTICO: PEDIRLO SIEMPRE: Si ya está concedido, Android no me lo pide
            Log.d("ETIQUETA_LOG", "PIDO PERMISOS PELIGROSOS pues estoy versión >= 6 ");
            requestPermissions(array_permisos, 999);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("ETIQUETA_LOG", "vuelta de pedir permisos reqcode = " + requestCode);



        if ((grantResults[0] == PackageManager.PERMISSION_GRANTED) && (grantResults[1] == PackageManager.PERMISSION_GRANTED))//0
        {
            Log.d("ETIQUETA_LOG", "Me ha dado permiso para acceder a las cuentas " );
            mostrarCuentas();

        } else {
            Log.d("ETIQUETA_LOG", "Me ha denegado el permiso para acceder a las cuentas ");
            Toast.makeText(this, "NO SE PUEDE LEER LAS CUENTAS, PERMISO DENEGADO", Toast.LENGTH_LONG).show();
            //finish();
        }

    }

    private void mostrarCuentas()
    {
        Log.d("ETIQUETA_LOG", "Mostrando las cuentas" );
        AccountManager accountManager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
        Account[] cuentas_usuario = accountManager.getAccounts();

        if (cuentas_usuario!=null)
        {
            Log.d("ETIQUETA_LOG", "num Cuentas usuario = " + cuentas_usuario.length );
        } else {
            Log.d("ETIQUETA_LOG", "Cuentas usuario es NULL" );
        }


        String tipo_cuenta = null;
        String nombre_cuenta = null;
        for (Account cuenta: cuentas_usuario)
        {
            tipo_cuenta = cuenta.type;
            nombre_cuenta = cuenta.name;
            Log.d("ETIQUETA_LOG " , "Tipo = " + tipo_cuenta + " y nombre = " + nombre_cuenta );
        }
    }
}