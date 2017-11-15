package br.com.capiwara.service;

import br.com.capiwara.entity.Imovel;
import br.com.capiwara.repository.ImovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;

@Service
public class ImovelService {

    @Autowired
    private ImovelRepository imovelRepository;

    public Imovel findById(Long id) throws Exception {
        Imovel imovel = imovelRepository.findOne(id);
        if (imovel == null) throw new Exception("Imovel não encontrado");
        return imovel;
    }

    public LinkedHashMap<String, String> calcularParcelas(Long id) throws Exception {
        LinkedHashMap<String, String> lista = new LinkedHashMap<>();
        DecimalFormat df = new DecimalFormat("#.00");
        Imovel imovel = findById(id);
        int i;
        for (i = 12; i < 360; i = i + 36) {
            if (i == 12) {
                lista.put("" + i / 12 + " ano/valor parcela por mes", df.format(imovel.getPreco() / i));

            } else {
                System.out.println("Valor d i: " + i);
                lista.put("" + i / 12 + " anos/valor parcela por mes", df.format(imovel.getPreco() / i));
            }
        }
        return lista;
    }
}
