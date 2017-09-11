package udemy.sqlite.curso.udemy.adolfo.com.sqlirteudemy.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import udemy.sqlite.curso.udemy.adolfo.com.sqlirteudemy.Models.Amigo;

/**
 * Created by Adolfo Chavez on 06/09/2017.
 */

public class AmigoOpenHelper extends SQLiteOpenHelper {

    //datos para la base de datos y las tablas
    public static final String DB_NAME = "db_amigos";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "tabla_amigos";

    //datos para las columnas de la tabla
    public static final String COL_ID = "id";
    public static final String COL_NOMBRE = "nombre";
    public static final String COL_APELLIDO = "apellido";
    public static final String COL_EDAD = "edad";
    public static final String COL_SEXO = "sexo";

    public AmigoOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //query para crear una tabla
    private String createTable =
            "create table "+TABLE_NAME+"("
                    +COL_ID+" integer primary key autoincrement, "
                    +COL_NOMBRE+" text, "
                    +COL_APELLIDO+" text, "
                    +COL_EDAD+" integer, "
                    +COL_SEXO+" text)";

    //aqui va la creacion de la base de datos junto a sus tablas
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //aqui por motivos de muestra usaremos drop table if exits
        //normalmente se hace una migracion de datos antes de borrar la table y hacer el upgrade
        db.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(db);
    }

    //aqui van los metodos que mandaremos a llamar desde nuestra actividad o actividades
    public void addAmigo(Amigo amigo){
        SQLiteDatabase mDatabase = getWritableDatabase();
        //para añadir datos usaremos ContentValues
        ContentValues mValues = new ContentValues();
        //se asignan los valores en forma de (Key,Value) - (nombre,adolfo)
        mValues.put(COL_NOMBRE,amigo.getNombre());
        mValues.put(COL_APELLIDO,amigo.getApellido());
        mValues.put(COL_EDAD,amigo.getEdad());
        mValues.put(COL_SEXO,amigo.getSexo());
        //se hace el insert, con parametros (nombre de la tabla,nullColumHack, contentvalues) - (TABLE_NAME,null,mValues)
        //nullColumHack explicacion -> https://stackoverflow.com/questions/10633299/insert-method-of-sqlitedatabase
        mDatabase.insert(TABLE_NAME,null,mValues);
        mDatabase.close();
    }

    //Aqui se hace un delete en sqlite
    public void deleteAmigo(int id){
        SQLiteDatabase mDatabase = getWritableDatabase();
        //para hacer un delete solo se llama el correspondiente metodo
        //y se le da de parametros (table name,where clause, where args)
        //explicacion de where args -> https://stackoverflow.com/questions/4560212/string-whereargs-parameter-of-database-method-in-android
        //se explica en la parte de abajo con un ejemplo, pudes probar con ambas
        mDatabase.delete(TABLE_NAME,COL_ID+"=?",new String[]{String.valueOf(id)});
        //mDatabase.delete(TABLE_NAME,COL_ID+"="+id,null);
    }

    //en esta parte se hace el update en sqlite
    public void updateAmigo(Amigo amigo, int id){
        SQLiteDatabase mDatabase = getWritableDatabase();
        ContentValues mValues = new ContentValues();
        mValues.put(COL_NOMBRE,amigo.getNombre());
        mValues.put(COL_APELLIDO,amigo.getApellido());
        mValues.put(COL_EDAD,amigo.getEdad());
        mValues.put(COL_SEXO,amigo.getSexo());
        //para hacer el update se piden los siugientes parametros
        //(table name,values, where clause, where args)
        //abajo se muestran las dos maneras, al igual que en el delete, se puede hacer de las dos maneras
        //utilzia la que mas te guste
        mDatabase.update(TABLE_NAME,mValues,COL_ID+"=?",new String[]{String.valueOf(id)});
        //mDatabase.update(TABLE_NAME,mValues,COL_ID+"="+id,null);
        mDatabase.close();
    }

    //aqui se hace un select pero solo para un elemento dela tabla
    public Amigo getAmigo(int id){
        Amigo amigo = new Amigo();
        SQLiteDatabase mDatabase = getWritableDatabase();
        //se crea un select y se guarda en un string
        String select = "select * from "+TABLE_NAME+" where "+COL_ID+" ="+id;
        //para obtener los resultados del select se usa un Cursor y se hace un rawQuery, query a la fila
        //con parametros (querySql,selectionArgs)
        Cursor mCursor = mDatabase.rawQuery(select,null);
        //se comprueba si el cursor se logro colocar en el primer elemento dle select
        if (mCursor.moveToFirst()){
            //si logro encontrarlo, se obtienen los datos del cursor, colocandolos dentro del objeto amigo credo anteriormente
            amigo.setId(Integer.parseInt(mCursor.getString(0)));
            amigo.setNombre(mCursor.getString(1));
            amigo.setApellido(mCursor.getString(2));
            amigo.setEdad(Integer.parseInt(mCursor.getString(3)));
            amigo.setSexo(mCursor.getString(4));
        }
        //se regresa un objeto amigo
        return amigo;
    }

    //aqui se obtienen todos los datos de la tabla
    public List<Amigo> getAllAmigos(){
        SQLiteDatabase mDatabase = getWritableDatabase();
        //se delcara una lista con parametro objeto amigo para llenrla
        List<Amigo> mAmigoList = new ArrayList<>();
        //select all query
        String sqlSelect = "select * from "+TABLE_NAME;
        Cursor mCursor = mDatabase.rawQuery(sqlSelect,null);
        if (mCursor.moveToFirst()){
            //al moverse al primero, se entra en un ciclo, donde obtiene los elemntos de la tabla
            do {
                Amigo mAmigo = new Amigo();
                mAmigo.setId(Integer.parseInt(mCursor.getString(0)));
                mAmigo.setNombre(mCursor.getString(1));
                mAmigo.setApellido(mCursor.getString(2));
                mAmigo.setEdad(Integer.parseInt(mCursor.getString(3)));
                mAmigo.setSexo(mCursor.getString(4));
                //se añade el objeto mAmigo en la lista
                mAmigoList.add(mAmigo);
                //mientras se pueda mover al siguiente, repetira el procesoanterior
            }while (mCursor.moveToNext());
        }
        //al final, ya que no tenga mas elementos, se regresara una lista
        return mAmigoList;
    }

}
