export function transformTextToCapitalize(text) {
  const words = text.toLowerCase().split('_');
  const capitalizedWords = words.map((word) => word.charAt(0).toUpperCase() + word.slice(1));
  return capitalizedWords.join(' ');
}
