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
import { SelectCliente } from '@/models/cliente';

interface ModalAddEditClienteProps {
  cliente?: SelectCliente; // Si viene es edición, si no es nuevo
  reloadClientes?: () => void; // Para recargar la lista después
}

const validationSchema = Yup.object({
  nombre: Yup.string().required("Nombre es requerido"),
  cedula: Yup.string().required("Cédula es requerida"),
  direccion: Yup.string(),
  telefono: Yup.string(),
  tope_credito: Yup.number().min(0, "Debe ser positivo"),
  estado: Yup.string().required("Estado requerido"),
});

const ModalAddEditCliente: React.FC<ModalAddEditClienteProps> = ({
  cliente,
  reloadClientes,
}) => {
  const formik = useFormik({
    initialValues: {
      nombre: cliente?.nombre || "",
      cedula: cliente?.cedula || "",
      direccion: cliente?.direccion || "",
      telefono: cliente?.telefono || "",
      es_nuevo: cliente?.es_nuevo ?? true,
      tope_credito: cliente?.tope_credito || 250000,
      estado: cliente?.estado || "activo",
    },
    validationSchema,
    enableReinitialize: true, // para que actualice si cambia cliente
    onSubmit: async (values, { resetForm }) => {
      try {
        if (cliente?.id) {
          // UPDATe fetch
          const res = await fetch('/api/cliente/'+cliente.id, {
            method: 'PUT',
            body: JSON.stringify(values),
            headers: { 'Content-Type': 'application/json' }
          }); 
        } else {
          // CREATE
          console.log(values);
          const res = await fetch('/api/cliente', {
            method: 'POST',
            body: JSON.stringify(values),
            headers: { 'Content-Type': 'application/json' }
        });
        
        }
        reloadClientes?.();
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
        {cliente ? (
          <Button variant="ghost" size="icon" title="Editar Cliente">
            <Pencil className="h-4 w-4" />
          </Button>
        ) : (
          <Button variant="default" size="sm" className="h-8 gap-1">
            <PlusCircle className="h-3.5 w-3.5" />
            <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
              Agregar Cliente
            </span>
          </Button>
        )}
      </ModalTrigger>
      <ModalContent>
        <ModalHeader>
          <ModalTitle>{cliente ? "Editar Cliente" : "Agregar Cliente"}</ModalTitle>
          <ModalDescription>
            {cliente
              ? "Edita los campos del cliente."
              : "Llena los campos para crear un nuevo cliente."}
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
            <label>Cédula</label>
            <Input
              name="cedula"
              value={formik.values.cedula}
              onChange={formik.handleChange}
              onBlur={formik.handleBlur}
            />
            {formik.touched.cedula && formik.errors.cedula && (
              <div className="text-red-500 text-sm">{formik.errors.cedula}</div>
            )}
          </div>

          <div>
            <label>Dirección</label>
            <Input
              name="direccion"
              value={formik.values.direccion}
              onChange={formik.handleChange}
            />
          </div>

          <div>
            <label>Teléfono</label>
            <Input
              name="telefono"
              value={formik.values.telefono}
              onChange={formik.handleChange}
            />
          </div>
          {/* <div>
            <label>Tope Crédito</label>
            <Input
              name="tope_credito"
              type="number"
              value={formik.values.tope_credito}
              onChange={formik.handleChange}
            />
          </div>

          <div>
            <label>Estado</label>
            <Input
              name="estado"
              value={formik.values.estado}
              onChange={formik.handleChange}
            />
          </div> */}

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

export default ModalAddEditCliente;
