import jwt_decode from 'jwt-decode';


interface tokenType {
    [key : string] : string
}

export class JWTToken {
    public static decodeToken(token: string) {
        return jwt_decode(token) as tokenType;
      }
    
      
    public static getUserFromToken(token: string) {
        var decodedToken = JWTToken.decodeToken(token);
        return decodedToken ? decodedToken['name'] : null; // TODO: checkk if this is how user details are retreived
    }

    public static isExpired(token: string) {
        var decodedToken = JWTToken.decodeToken(token)!;
        const expTime: number = parseInt(decodedToken['exp']);
        return ((1000 * expTime) - (new Date()).getTime()) < 5000;
    }
}