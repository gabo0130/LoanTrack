"use client";

import React, { useEffect } from "react";
import {
  Modal,
  ModalTrigger,
  ModalContent,
  ModalHeader,
  ModalFooter,
  ModalTitle,
  ModalDescription,
  ModalClose,
} from "@/components/ui/modal";
import { PlusCircle, Pencil } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { useFormik } from "formik";
import * as Yup from "yup";
import { SelectProducto } from '@/models/producto';

interface ModalAddEditProductoProps {
  producto?: SelectProducto; // Si viene es edición, si no es nuevo
  reloadProductos?: () => void; // Para recargar la lista después
}

const validationSchema = Yup.object({
  nombre: Yup.string().required("Nombre es requerido"),
  descripcion: Yup.string().required("Descripción es requerida"),
  precio: Yup.number()
    .required("Precio es requerido")
});

const ModalAddEditProducto: React.FC<ModalAddEditProductoProps> = ({
  producto,
  reloadProductos,
}) => {
  const formik = useFormik({
    initialValues: {
      nombre: producto?.nombre || "",
      descripcion: producto?.descripcion || "",
      precio: producto?.precio || 0,
    },
    validationSchema,
    enableReinitialize: true, // para que actualice si cambia producto
    onSubmit: async (values, { resetForm }) => {
      try {
        if (producto?.id) {
          // UPDATe fetch
          const res = await fetch('/api/producto/'+producto.id, {
            method: 'PUT',
            body: JSON.stringify(values),
            headers: { 'Content-Type': 'application/json' }
          }); 
        } else {
          // CREATE
          console.log(values);
          const res = await fetch('/api/producto', {
            method: 'POST',
            body: JSON.stringify(values),
            headers: { 'Content-Type': 'application/json' }
        });
        
        }
        reloadProductos?.();
        resetForm();

        document.getElementById("close-modal")?.click(); // Cerrar modal
      } catch (error) {
        console.error(error);
      }
    },
  });

  return (
    <Modal>
      <ModalTrigger asChild>
        {producto ? (
          <Button variant="ghost" size="icon" title="Editar Producto">
            <Pencil className="h-4 w-4" />
          </Button>
        ) : (
          <Button variant="default" size="sm" className="h-8 gap-1">
            <PlusCircle className="h-3.5 w-3.5" />
            <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
              Agregar Producto
            </span>
          </Button>
        )}
      </ModalTrigger>
      <ModalContent>
        <ModalHeader>
          <ModalTitle>{producto ? "Editar Producto" : "Agregar Producto"}</ModalTitle>
          <ModalDescription>
            {producto
              ? "Edita los campos del producto."
              : "Llena los campos para crear un nuevo producto."}
          </ModalDescription>
        </ModalHeader>

        <form onSubmit={formik.handleSubmit} className="space-y-4 mt-4">
          <div>
            <label>Nombre</label>
            <Input
              name="nombre"
              value={formik.values.nombre}
              onChange={formik.handleChange}
              onBlur={formik.handleBlur}
            />
            {formik.touched.nombre && formik.errors.nombre && (
              <div className="text-red-500 text-sm">{formik.errors.nombre}</div>
            )}
          </div>

          <div>
            <label>Descripcion</label>
            <Input
              name="descripcion"
              value={formik.values.descripcion}
              onChange={formik.handleChange}
              onBlur={formik.handleBlur}
            />
            {formik.touched.descripcion && formik.errors.descripcion && (
              <div className="text-red-500 text-sm">{formik.errors.descripcion}</div>
            )}
          </div>

          <div>
            <label>Precio</label>
            <Input
              name="precio"
              value={formik.values.precio}
              onChange={formik.handleChange}
            />
          </div>

          <ModalFooter>
            <Button type="submit">Guardar</Button>
            <ModalClose asChild>
              <Button id="close-modal" variant="secondary" type="button">
                Cancelar
              </Button>
            </ModalClose>
          </ModalFooter>
        </form>
      </ModalContent>
    </Modal>
  );
};

export default ModalAddEditProducto;
