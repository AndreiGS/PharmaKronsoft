import { City } from './city';
import { Country } from './country';

export interface User {
  username: string;
  firstName: string;
  lastName: string;
  street: string;
  city: City;
  country: Country;
}
