package projetosistemasmoveis.rastreador.TelaDispositivos;

import projetosistemasmoveis.rastreador.Objetos.ObjDispositivoStatus;

public interface GetDispositivosCallback {
    void onDispositivosLoaded(ObjDispositivoStatus[] objetos);
    void onError();
}