package com.udc.fic.ipm.mb.tetocaati.controlador;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.udc.fic.ipm.mb.tetocaati.R;
import com.udc.fic.ipm.mb.tetocaati.modelo.AlmacenCategoriaModelo;
import com.udc.fic.ipm.mb.tetocaati.modelo.CategoriaModelo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
//CONTROLADOR

    private AlmacenCategoriaModelo aCM;
    private ArrayAdapter<String> adapter;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.lista) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            //menu.setHeaderTitle(Countries[info.position]);
            menu.add(Menu.NONE, 0, 0, "Editar");
            menu.add(Menu.NONE, 1, 1, "Borrar");
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int menuSeleccionado = item.getItemId();
        final int indexCategoria = info.position;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ListView listView = (ListView) findViewById(R.id.lista);
        final String nombreCategoria = (String) listView.getItemAtPosition(indexCategoria);

        if (menuSeleccionado == 1) /*BORRAR*/{

            builder.setTitle("Se borrará la categoría: "+nombreCategoria);

            // Set up the buttons
            builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                @Override
                public void onClick (DialogInterface dialog,int which){
                    if (aCM.delCategoria(indexCategoria)){
                        adapter.clear();
                        adapter.addAll(aCM.getListaCategoriasNombre());
                        adapter.notifyDataSetChanged();
                        System.out.println(aCM.getListaCategoriasNombre().toString());
                    }
                    else{
                        System.out.println("ERROR AL BORRAR");
                    }
                }
            });

            builder.setNegativeButton("Cancelar",new DialogInterface.OnClickListener(){
                @Override
                public void onClick (DialogInterface dialog,int which){
                    dialog.cancel();
                }
            });
        }
        else{
            builder.setTitle("Editar categoría: "+nombreCategoria);
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setText(nombreCategoria);
            builder.setView(input);
            // Set up the buttons
            builder.setPositiveButton("Confirmar",new DialogInterface.OnClickListener(){
                @Override
                public void onClick (DialogInterface dialog,int which){
                    String m_Text = input.getText().toString();
                    if (aCM.editCategoria(indexCategoria, m_Text)){
                        adapter.clear();
                        adapter.addAll(aCM.getListaCategoriasNombre());
                        adapter.notifyDataSetChanged();
                        System.out.println(aCM.getListaCategoriasNombre().toString());
                    }
                    else{
                        System.out.println("ERROR AL EDITAR");
                    }
                }
            });

            builder.setNegativeButton("Cancelar",new DialogInterface.OnClickListener(){
                @Override
                public void onClick (DialogInterface dialog,int which){
                    dialog.cancel();
                }
            });
        }
        builder.show();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get ListView object from xml
        final ListView listView = (ListView) findViewById(R.id.lista);

        aCM = new AlmacenCategoriaModelo(getPreferences(MODE_PRIVATE));
        // Defined Array values to show in ListView
        ArrayList<String> values = (ArrayList<String>) aCM.getListaCategoriasNombre();

        // Define a new Adapter

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);
        // Register context menu
        registerForContextMenu(listView);
    }

    public void onClick_vPrincipal_bAnadir(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Añadir Categoría");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
            @Override
            public void onClick (DialogInterface dialog,int which){
                String m_Text = input.getText().toString();
                CategoriaModelo cM = new CategoriaModelo(m_Text);
                aCM.addCategoria(cM);
                System.out.println(aCM.getListaCategoriasNombre().toString());
                aCM.saveAlmacenCategoriaModelo(getPreferences(MODE_PRIVATE), aCM);
                adapter.clear();
                adapter.addAll(aCM.getListaCategoriasNombre());
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancelar",new DialogInterface.OnClickListener(){
            @Override
            public void onClick (DialogInterface dialog,int which){
                dialog.cancel();
            }
        });
        builder.show();

    }

}
