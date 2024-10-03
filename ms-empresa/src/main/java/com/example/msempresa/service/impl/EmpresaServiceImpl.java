package com.example.msempresa.service.impl;

import com.example.msempresa.dto.UserDto;
import com.example.msempresa.entity.Empresa;
import com.example.msempresa.feign.UserFeign;
import com.example.msempresa.repository.EmpresaRepository;
import com.example.msempresa.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class EmpresaServiceImpl implements EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private UserFeign userFeign;

    @Override
    public List<Empresa> list() {
        // Obtener todas las empresas desde la base de datos
        List<Empresa> empresas = empresaRepository.findAll();

        // Para cada empresa, obtener el UserDto asociado
        for (Empresa empresa : empresas) {
            // Verifica que el userid no sea nulo
            if (empresa.getUserid() != null) {
                Optional<UserDto> userDtoOptional = userFeign.getById(empresa.getUserid()); // Llamada directa

                // Verifica si el UserDto está presente
                if (userDtoOptional.isPresent()) {
                    empresa.setUserDto(userDtoOptional.get()); // Usa el setter para establecer UserDto
                } else {
                    System.out.println("Usuario no encontrado para el id: " + empresa.getUserid());
                    empresa.setUserDto(null); // O asignar un objeto vacío si prefieres
                }
            } else {
                System.out.println("userid es nulo para la empresa con id: " + empresa.getId());
            }
        }

        return empresas; // Retorna la lista de empresas con UserDto
    }

    @Override
    public Optional<Empresa> getById(Integer id) {
        Optional<Empresa> empresaOpt = empresaRepository.findById(id);

        if (empresaOpt.isPresent()) {
            Empresa empresa = empresaOpt.get(); // Obtener la instancia de Empresa

            // Verifica que el userid no sea nulo
            if (empresa.getUserid() != null) {
                Optional<UserDto> userDtoOptional = userFeign.getById(empresa.getUserid()); // Llamada directa

                // Verifica si userDto está presente
                if (userDtoOptional.isPresent()) {
                    empresa.setUserDto(userDtoOptional.get()); // Usa el setter para establecer UserDto
                } else {
                    System.out.println("Usuario no encontrado para el id: " + empresa.getUserid());
                    empresa.setUserDto(null); // O asignar un objeto vacío si prefieres
                }
            } else {
                System.out.println("userid es nulo para la empresa con id: " + id);
            }
        }

        return empresaOpt; // Retorna el Optional<Empresa>
    }
    @Override
    public Empresa save(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    @Override
    public Empresa update(Integer id, Empresa empresa) {
        empresa.setId(id);
        return empresaRepository.save(empresa);
    }

    @Override
    public void delete(Integer id) {
        empresaRepository.deleteById(id);
    }
}
