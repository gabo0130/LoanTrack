import React, { useState, useEffect } from 'react';
import { Input } from '@/components/ui/input';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { useAlert } from '../AlertContext';

interface Option {
  id: number;
  nombre: string;
}

interface SearchableSelectProps {
  label: string;
  endpoint: string; // API endpoint para buscar
  onSelect: (option: Option) => void;
  initialValue?: number | null;
}

const SearchableSelect: React.FC<SearchableSelectProps> = ({ label, endpoint, onSelect, initialValue }) => {
  const [search, setSearch] = useState('');
  const [options, setOptions] = useState<Option[]>([]);
  const [selected, setSelected] = useState<Option | null>(null);
  const { showAlert } = useAlert();

  useEffect(() => {
    const fetchOptions = async () => {
      try {
        const res = await fetch(`${endpoint}?q=${search.toString()}`, {
          cache: 'no-store'
        });

        if (!res.ok) {
          throw new Error('Error fetching options');
        }

        const data = (await res.json()) as Option[];
        if (data.length === 0) {
          showAlert(`No se encontraron resultados de ${search.toString()}`, 'warning');
        }
        setOptions(data);
      } catch (error) {
        console.error('Error fetching options:', error);
      }
    };

    if (search.length >= 2 || search.length === 0) {
      fetchOptions();
    }
  }, [search, endpoint]);

  return (
    <div className="space-y-2">
      <label className="block text-sm font-medium">{label}</label>
      <Input
        placeholder={`Buscar ${label.toLowerCase()}...`}
        value={search}
        onChange={(e) => setSearch(e.target.value)}
      />
      <Select onValueChange={(value) => {
        const option = options.find(opt => opt.id.toString() === value);
        if (option) {
          setSelected(option);
          onSelect(option);
        }
      }}>
        <SelectTrigger>
          <SelectValue placeholder={`Seleccione ${label.toLowerCase()}`} />
        </SelectTrigger>
        <SelectContent>
          {options.map(option => (
            <SelectItem key={option.id} value={option.id.toString()}>
              {option.nombre}
            </SelectItem>
          ))}
        </SelectContent>
      </Select>
    </div>
  );
};

export default SearchableSelect;
