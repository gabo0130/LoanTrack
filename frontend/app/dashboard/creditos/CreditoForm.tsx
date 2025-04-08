'use client';
import React, { useMemo, useState } from 'react';
import {
  Modal,
  ModalTrigger,
  ModalContent,
  ModalHeader,
  ModalFooter,
  ModalTitle,
  ModalDescription,
  ModalClose
} from '@/components/ui/modal';
import { PlusCircle, Pencil } from 'lucide-react';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import { SelectCredito } from '@/models/credito';
import SearchableSelect from '@/components/ui/searchableSelect';
import CustomSelect from '@/components/ui/CustomSelect';
import { useAlert } from '@/components/AlertContext';

interface ModalAddEditClienteProps {
  credito?: SelectCredito;
  reloadCreditos?: () => void;
}

const optionsEstados = [
  { id: 'abierto', nombre: 'Abierto' },
  { id: 'cerrado', nombre: 'Cerrado' },
  { id: 'cancelado', nombre: 'Cancelado' },
  { id: 'moroso', nombre: 'Moroso' }
];

const optionsTipoCredito = [
  { id: 'PRODUCTO', nombre: 'Producto' },
  { id: 'DINERO', nombre: 'Dinero' }
];

const validationSchema = Yup.object({
  id_cliente: Yup.number().required('El cliente es requerido'),
  tipo_credito: Yup.string().required('El tipo de crédito es requerido'),
  monto_total: Yup.number().required('El monto total es requerido'),
  plazo_cuotas: Yup.number().required('El plazo de cuotas es requerido'),
  fecha_inicio: Yup.date(),
  fecha_fin: Yup.date(),
  estado: Yup.string().required('El estado es requerido'),
  id_producto: Yup.number().when('tipo_credito', {
    is: 'PRODUCTO',
    then: (validationSchema) =>
      validationSchema.required('El producto es requerido')
  }),
  tasa_interes: Yup.number()
    .nullable()
    .when('tipo_credito', {
      is: 'DINERO',
      then: (validationSchema) =>
        validationSchema.required('La tasa de interés es requerida')
    })
});

const ModalAddEditCliente: React.FC<ModalAddEditClienteProps> = ({
  credito,
  reloadCreditos
}) => {
  const [tipoCredito, setTipoCredito] = useState(credito?.tipo_credito || '');

  const { showAlert } = useAlert();
  const initialValues = useMemo(
    () => ({
      id_cliente: credito?.id_cliente || null,
      id_producto: credito?.id_producto || null,
      tipo_credito: credito?.tipo_credito || null,
      monto_total: credito?.monto_total || null,
      plazo_cuotas: credito?.plazo_cuotas || null,
      fecha_inicio: credito?.fecha_inicio || new Date(),
      estado: credito?.estado || '',
      tasa_interes: credito?.tasa_interes || null
    }),
    [credito]
  );

  const formik = useFormik({
    initialValues,
    validationSchema,
    enableReinitialize: true,
    onSubmit: async (values, { resetForm }) => {
      try {
        if (credito?.id) {
          await fetch('/api/credito/' + credito.id, {
            method: 'PUT',
            body: JSON.stringify(values),
            headers: { 'Content-Type': 'application/json' }
          });
        } else {
          await fetch('/api/credito', {
            method: 'POST',
            body: JSON.stringify(values),
            headers: { 'Content-Type': 'application/json' }
          });
        }
        showAlert(
          `Credito ${credito ? 'actualizado' : 'creado'} con éxito`,
          'success',
          3000
        );
        reloadCreditos?.();
        resetForm();
        document.getElementById('close-modal')?.click();
      } catch (error) {
        showAlert(
          `Error al ${credito ? 'actualizar' : 'crear'} el credito`,
          'error',
          3000
        );
        console.error(error);
      }
    }
  });

  const handleTipoCreditoChange = (
    event: React.ChangeEvent<HTMLSelectElement>
  ) => {
    const value = event.target.value;
    setTipoCredito(value);
    formik.setFieldValue('tipo_credito', value);
    // Resetear campos dependientes al cambiar tipo
    if (value === 'producto') {
      formik.setFieldValue('tasa_interes', 0);
    } else if (value === 'dinero') {
      formik.setFieldValue('id_producto', 0);
    }
  };

  return (
    <Modal>
      <ModalTrigger asChild>
        {credito ? (
          <Button variant="ghost" size="icon" title="Editar Credito">
            <Pencil className="h-4 w-4" />
          </Button>
        ) : (
          <Button variant="default" size="sm" className="h-8 gap-1">
            <PlusCircle className="h-3.5 w-3.5" />
            <span className="sr-only sm:not-sr-only sm:whitespace-nowrap">
              Agregar Credito
            </span>
          </Button>
        )}
      </ModalTrigger>
      <ModalContent>
        <ModalHeader>
          <ModalTitle>
            {credito ? 'Editar Credito' : 'Agregar Credito'}
          </ModalTitle>
          <ModalDescription>
            {credito
              ? 'Edita los campos del credito.'
              : 'Llena los campos para crear un nuevo credito.'}
          </ModalDescription>
        </ModalHeader>

        <form onSubmit={formik.handleSubmit} className="space-y-4 mt-4">
          {/* Cliente */}
          {/* Renderizar campos según si es edición o creación */}
          {credito ? (
            <>
              {/* Monto Total */}
              <div>
                <label htmlFor="monto_total">Monto Total</label>
                <Input
                  id="monto_total"
                  name="monto_total"
                  type="number"
                  value={formik.values.monto_total ?? ''}
                  onChange={formik.handleChange}
                  onBlur={formik.handleBlur}
                  className="form-input"
                />
                {formik.touched.monto_total && formik.errors.monto_total ? (
                  <div className="text-red-500">
                    {formik.errors.monto_total}
                  </div>
                ) : null}
              </div>

              {/* Plazo Cuotas */}
              <div>
                <label htmlFor="plazo_cuotas">Plazo Cuotas</label>
                <Input
                  id="plazo_cuotas"
                  name="plazo_cuotas"
                  type="number"
                  value={formik.values.plazo_cuotas ?? ''}
                  onChange={formik.handleChange}
                  onBlur={formik.handleBlur}
                  className="form-input"
                />
                {formik.touched.plazo_cuotas && formik.errors.plazo_cuotas ? (
                  <div className="text-red-500">
                    {formik.errors.plazo_cuotas}
                  </div>
                ) : null}
              </div>

              {/* Estado */}
              <div>
                <CustomSelect
                  label="Estado"
                  options={optionsEstados}
                  selectedOption={optionsEstados.find(
                    (opt) => opt.id === formik.values.estado
                  )}
                  onSelect={(option) =>
                    formik.setFieldValue('estado', option.id)
                  }
                  placeholder="Seleccione un Estado"
                />
                {formik.touched.estado && formik.errors.estado ? (
                  <div className="text-red-500">{formik.errors.estado}</div>
                ) : null}
              </div>

              {/* Tasa de Interés */}
              {tipoCredito === 'DINERO' && (
                <div>
                  <label htmlFor="tasa_interes">Tasa de Interés</label>
                  <Input
                    id="tasa_interes"
                    name="tasa_interes"
                    type="number"
                    value={formik.values.tasa_interes ?? ''}
                    onChange={formik.handleChange}
                    onBlur={formik.handleBlur}
                    className="form-input"
                  />
                  {formik.touched.tasa_interes && formik.errors.tasa_interes ? (
                    <div className="text-red-500">
                      {formik.errors.tasa_interes}
                    </div>
                  ) : null}
                </div>
              )}
            </>
          ) : (
            <>
              {/* Campos para creación */}
              {/* Cliente */}
              <div>
                <SearchableSelect
                  label="Cliente"
                  endpoint="/api/cliente/search"
                  onSelect={(cliente) =>
                    formik.setFieldValue('id_cliente', cliente.id)
                  }
                />
                {formik.touched.id_cliente && formik.errors.id_cliente ? (
                  <div className="text-red-500">{formik.errors.id_cliente}</div>
                ) : null}
              </div>

              {/* Tipo de Credito */}
              <div>
                <CustomSelect
                  label="Tipo producto"
                  options={optionsTipoCredito}
                  selectedOption={optionsTipoCredito.find(
                    (opt) => opt.id === tipoCredito
                  )}
                  onSelect={(option) =>
                    handleTipoCreditoChange({
                      target: { value: option.id }
                    } as any)
                  }
                  placeholder="Seleccione un Tipo de producto"
                />
                {formik.touched.tipo_credito && formik.errors.tipo_credito ? (
                  <div className="text-red-500">
                    {formik.errors.tipo_credito}
                  </div>
                ) : null}
              </div>

              {/* Producto solo si es producto */}
              {tipoCredito === 'PRODUCTO' && (
                <div>
                  <SearchableSelect
                    label="Producto"
                    endpoint="/api/producto/search"
                    onSelect={(producto) =>
                      formik.setFieldValue('id_producto', producto.id)
                    }
                  />
                  {formik.touched.id_producto && formik.errors.id_producto ? (
                    <div className="text-red-500">
                      {formik.errors.id_producto}
                    </div>
                  ) : null}
                </div>
              )}

              {/* Otros campos */}
              {/* Monto Total */}
              <div>
                <label htmlFor="monto_total">Monto Total</label>
                <Input
                  id="monto_total"
                  name="monto_total"
                  type="number"
                  value={formik.values.monto_total ?? ''}
                  onChange={formik.handleChange}
                  onBlur={formik.handleBlur}
                  className="form-input"
                />
                {formik.touched.monto_total && formik.errors.monto_total ? (
                  <div className="text-red-500">
                    {formik.errors.monto_total}
                  </div>
                ) : null}
              </div>

              {/* Plazo Cuotas */}
              <div>
                <label htmlFor="plazo_cuotas">Plazo Cuotas</label>
                <Input
                  id="plazo_cuotas"
                  name="plazo_cuotas"
                  type="number"
                  value={formik.values.plazo_cuotas ?? ''}
                  onChange={formik.handleChange}
                  onBlur={formik.handleBlur}
                  className="form-input"
                />
                {formik.touched.plazo_cuotas && formik.errors.plazo_cuotas ? (
                  <div className="text-red-500">
                    {formik.errors.plazo_cuotas}
                  </div>
                ) : null}
              </div>

              {/* Estado */}
              <div>
                <CustomSelect
                  label="Estado"
                  options={optionsEstados}
                  selectedOption={optionsEstados.find(
                    (opt) => opt.id === formik.values.estado
                  )}
                  onSelect={(option) =>
                    formik.setFieldValue('estado', option.id)
                  }
                  placeholder="Seleccione un Estado"
                />
                {formik.touched.estado && formik.errors.estado ? (
                  <div className="text-red-500">{formik.errors.estado}</div>
                ) : null}
              </div>
            </>
          )}

          <ModalFooter className="flex justify-end gap-2 mt-6">
            <ModalClose
              asChild
              id="close-modal"
              onClick={() => formik.resetForm()}
            >
              <Button variant="outline">Cancelar</Button>
            </ModalClose>
            <Button type="submit">{credito ? 'Actualizar' : 'Agregar'}</Button>
          </ModalFooter>
        </form>
      </ModalContent>
    </Modal>
  );
};

export default ModalAddEditCliente;
