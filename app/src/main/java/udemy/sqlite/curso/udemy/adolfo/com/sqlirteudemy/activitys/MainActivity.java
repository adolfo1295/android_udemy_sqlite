package udemy.sqlite.curso.udemy.adolfo.com.sqlirteudemy.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import udemy.sqlite.curso.udemy.adolfo.com.sqlirteudemy.Models.Amigo;
import udemy.sqlite.curso.udemy.adolfo.com.sqlirteudemy.R;
import udemy.sqlite.curso.udemy.adolfo.com.sqlirteudemy.database.AmigoOpenHelper;

public class MainActivity extends AppCompatActivity {

    private AmigoOpenHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //se instancia el helper para poder utilizar sus metodos, dandole de parametro el contexto
        mHelper = new AmigoOpenHelper(this);
    }

    //metodo para agregar un elemento a la base de datos
    public void addAmigo(View view) {
        for (int i = 1;i<=10;i++){
            //se manda a llamar el metodo addAmigo, dandole como parametro un objeto amigo
            mHelper.addAmigo(new Amigo("adolfo"+i,"chavez",20,"H"));
        }
        Toast.makeText(this, "Contacto añadido", Toast.LENGTH_SHORT).show();
    }

    //metodo para eliminar un elemento de la base de datos
    public void deleteContact(View view) {
        int usuarioAEliminar = 1;
        mHelper.deleteAmigo(usuarioAEliminar);
        Toast.makeText(this, "Amigo eliminado", Toast.LENGTH_SHORT).show();
    }

    //con este metodo se obtienen toodos los elementos de la tabla
    public void getAmigos(View view) {

        //incluso aqui se puede obtener los datos de un solo elemento, dandole un id para buscarlo
        Amigo newAmigo = mHelper.getAmigo(1);
        Toast.makeText(this, "Amigo: "+newAmigo.getNombre()+" "+newAmigo.getApellido(), Toast.LENGTH_SHORT).show();
        List<Amigo> mList = mHelper.getAllAmigos();
        for (int i = 0; i<mList.size();i++){
            Log.d("main","numero: "+mList.get(i).getId()+", nombre: "+mList.get(i).getNombre());
        }
    }

    //aqui se hace el uodate de un elemtno en la base de datos
    public void updateAmigo(View view) {
        Amigo updateAmigo = new Amigo("adolfin delfin","chavezin",22,"Macho");
        mHelper.updateAmigo(updateAmigo,2);
    }
}
