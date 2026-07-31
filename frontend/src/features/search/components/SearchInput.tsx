"use client";

import { Input } from "@/components/ui/input";
import { Search } from "lucide-react";
import { useState, useEffect } from "react";
import { useDebounce } from "@/hooks/useDebounce";

interface SearchInputProps {
  onSearch: (query: string) => void;
  isLoading?: boolean;
}

export function SearchInput({ onSearch, isLoading }: SearchInputProps) {
  const [value, setValue] = useState("");
  const debouncedValue = useDebounce(value, 500);

  useEffect(() => {
    onSearch(debouncedValue);
  }, [debouncedValue, onSearch]);

  return (
    <div className="relative max-w-xl w-full mx-auto">
      <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none text-muted-foreground">
        <Search className={`h-5 w-5 ${isLoading ? 'animate-pulse' : ''}`} />
      </div>
      <Input
        type="search"
        placeholder="Search for albums..."
        value={value}
        onChange={(e) => setValue(e.target.value)}
        className="pl-10 h-12 rounded-full border-border bg-card shadow-sm text-base focus-visible:ring-primary"
      />
    </div>
  );
}
