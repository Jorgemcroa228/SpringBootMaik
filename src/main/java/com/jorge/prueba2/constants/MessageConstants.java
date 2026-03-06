package com.jorge.prueba2.constants;

public class MessageConstants {
    
   
    public final static String MESSAGE_ITEM_DELETE_ID = "Item eliminado con exito";
   
    public final static String MESSAGE_ID_CERO_O_NEGATIVE = "El id no puede ser negativo o cero";
    public final static String MESSAGE_NAME_REQUIRED = "El nombre del item es requerido y no puede estar vacío";
    public final static String MESSAGE_CANTIDAD_INVALID = "La cantidad debe ser mayor a 0";
    public final static String MESSAGE_MARCA_REQUIRED = "La marca del item es requerida y no puede estar vacía";
    public final static String MESSAGE_MODELO_REQUIRED = "El modelo del item es requerido y no puede estar vacío";
    public final static String MESSAGE_ITEM_NOT_FOUND = "El item con id " + "%d" + " no existe";
    
    public final static String MESSAGE_BUY_SUCCESS = "Compra realizada con exito. Cantidad descontada: %d";
    public final static String MESSAGE_BUY_INSUFFICIENT_STOCK = "Stock insuficiente. Disponible: %d, Solicitado: %d";
}
