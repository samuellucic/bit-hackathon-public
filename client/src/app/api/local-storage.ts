export type LocalStorageKey = 'auth-id-token' | 'authority';

export function getItem(key: LocalStorageKey): string | null {
  return localStorage.getItem(key);
}

export function setItem(key: LocalStorageKey, value: string) {
  localStorage.setItem(key, value);
}

export function removeItem(key: LocalStorageKey) {
  localStorage.removeItem(key);
}
