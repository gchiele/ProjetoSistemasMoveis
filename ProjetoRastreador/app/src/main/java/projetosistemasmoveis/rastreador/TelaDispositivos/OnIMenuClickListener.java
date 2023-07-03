package projetosistemasmoveis.rastreador.TelaDispositivos;

import android.view.View;

import projetosistemasmoveis.rastreador.Objetos.ObjDispositivoStatus;

public interface OnIMenuClickListener {
    void onMenuClick(int position, ObjDispositivoStatus dispositivo, View view);
}
