package com.LoanTrack.Backend.Credit_product_service.service;

import com.LoanTrack.Backend.Credit_product_service.entity.Producto;
import com.LoanTrack.Backend.Credit_product_service.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // Crear un nuevo producto
    @Transactional
    public Producto crearProducto(Producto producto) {
        validarProducto(producto);
        return productoRepository.save(producto);
    }

    // Obtener producto por ID
    public Producto obtenerProductoPorId(Integer id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }

    // Actualizar producto
    @Transactional
    public Producto actualizarProducto(Integer id, Producto productoActualizado) {
        Producto productoExistente = obtenerProductoPorId(id);

        productoExistente.setNombre(productoActualizado.getNombre());
        productoExistente.setDescripcion(productoActualizado.getDescripcion());
        productoExistente.setPrecio(productoActualizado.getPrecio());


        validarProducto(productoExistente);
        return productoRepository.save(productoExistente);
    }

    // Listar todos los productos
    public List<Producto> listarTodosProductos() {
        return productoRepository.findAll();
    }


    // Buscar productos por nombre
    public List<Producto> buscarProductosPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // Buscar productos por rango de precio
    public List<Producto> buscarProductosPorRangoPrecio(BigDecimal min, BigDecimal max) {
        return productoRepository.findByPrecioBetween(min, max);
    }

    // Buscar productos con stock disponible




    // Validación de producto
    private void validarProducto(Producto producto) {
        if (producto.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El precio debe ser mayor a cero");
        }


        if (producto.getNombre() == null || producto.getNombre().isBlank()) {
            throw new RuntimeException("El nombre es requerido");
        }
    }

    // Eliminar producto (físico)
    @Transactional
    public void eliminarProducto(Integer id) {
        productoRepository.deleteById(id);
    }

}
