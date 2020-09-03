export enum ERole {
    USER = 'ROLE_USER',
    ADMIN = 'ROLE_ADMIN',
    MODERATOR = 'ROLE_MODERATOR'
}

export const user: ERole[] = [ERole.USER]

export const admin: ERole[] = [ERole.ADMIN]

export const moderator: ERole[] = [ERole.MODERATOR]

export const userAndAdmin: ERole[] = [ERole.USER, ERole.ADMIN];

export const userAndModerator: ERole[] = [ERole.USER, ERole.MODERATOR];

export const adminAndModerator: ERole[] = [ERole.ADMIN, ERole.MODERATOR];

export const all: ERole[] = [ERole.USER, ERole.ADMIN, ERole.MODERATOR];