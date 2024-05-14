package dev.levimaciell.Screenmatch_v2.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classe);
}
