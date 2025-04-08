'use client';

import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue
} from '@/components/ui/select';
import { cn } from 'lib/utils'; // Opcional: Para manejar clases dinámicas
import { useState } from 'react';

type Option = {
  id: string | number;
  nombre: string;
};

interface CustomSelectProps extends React.ComponentProps<typeof Select> {
  label?: string;
  options: Option[];
  selectedOption?: Option;
  onSelect: (option: Option) => void;
  placeholder?: string;
  className?: string;
}

export default function CustomSelect({
  label,
  options,
  selectedOption,
  onSelect,
  placeholder = 'Seleccione una opción',
  className,
  ...props
}: CustomSelectProps) {
  const [selected, setSelected] = useState<Option | undefined>(selectedOption);

  const handleChange = (value: string) => {
    const option = options.find((opt) => opt.id.toString() === value);
    if (option) {
      setSelected(option);
      onSelect(option);
    }
  };

  return (
    <div className={cn('flex flex-col gap-2 w-full', className)}>
      {label && <label className="text-sm font-semibold">{label}</label>}
      <Select
        onValueChange={handleChange}
        value={selected?.id.toString() ?? ''}
        {...props}
      >
        <SelectTrigger>
          <SelectValue
            placeholder={placeholder || `Seleccione ${label?.toLowerCase()}`}
          />
        </SelectTrigger>
        <SelectContent>
          {options.map((option) => (
            <SelectItem key={option.id} value={option.id.toString()}>
              {option.nombre}
            </SelectItem>
          ))}
        </SelectContent>
      </Select>
    </div>
  );
}
