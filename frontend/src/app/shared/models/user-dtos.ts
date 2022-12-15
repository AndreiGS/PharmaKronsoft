import { Country } from './country';
import { City } from './city';

export interface UserLoginDTO {
  username: string;
  password: string;
}

export interface UserRegisterDto {
  username: string;
  password: string;
  confirmPassword: string;
  firstName: string;
  lastName: string;
  street: string;
  city: City;
  country: Country;
  acceptedTerms: boolean;
}
