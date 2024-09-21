

# Convenio de reglas de uso de Git:
(para leer mejor este instructivo en un editor de codigo aprete las teclas ctrl + v + mayús)
Este archivo tiene el objetivo de ser un ayuda memoria y un recopilatorio de buenas prácticas en git.


## Crear rama:
Lo primero es saber en qué rama estas y el estado de la misma:
git status

En caso de no estar en la rama main moverse a la misma:
git checkout main

Ya en la main lo siguiente es actualizarla:
git pull origin main

Estamos listos para crear la rama el comando es el siguiente:
git checkout -b FuncionDeLaRama/NombreRama (ver convención de nombres de rama más abajo)

## como subir tus cambios en tu rama

Primero vemos el estado de la rama con el comando:
git status

En el mensaje que te tira en consola veras en que rama estas y los archivos modificados si hay. en rojo los archivos que no están agregados y en verdes los que están agregados.

El siguiente paso es agregar los cambios al paquete que vas a subir:
git add .
(este comado agrega todos los archivos de color rojo a el paquete que suviremos)

Ahora etiquetamos el paquete con un commit:

git commit -m "mensaje que identifica el pull"

Lo último a hacer es hacer un envío de datos a tu rama:

git pull origin NombreRama

## Actualizar tu rama (esto es muuuuuy importantes antes de  hacer tu ultimo pull):
Para traerte los cambios y las ramas actualizadas los pasos a seguir son:

comando para saber en qué rama estas y el estado de la misma se usa el comando:
git status

Luego de ver en qué rama estas en necesario que te muevas a la rama main para actualizar tu rama:
git checkout main

El siguiente paso es actualizar tu rama main :
git pull origin main

luego regresamos a la rama  que queremos actualizar :
git checkout nombrerama

toca mergear:
git merge main

y con eso te traes todos los cambios de la main (es posible que tengas que resolver conflictos)



## Convenciones de Nombres de Ramas

### feature/ - Para nuevas funcionalidades o características.
Ejemplo: feature/add-user-profile
Uso: Para desarrollar y agregar nuevas funcionalidades o características al proyecto.

### bugfix/ - Para corrección de errores.

Ejemplo: bugfix/fix-login-issue
Uso: Para resolver errores o problemas que han sido identificados en el proyecto.

### enhancement/ - Para mejoras de funcionalidades existentes.

Ejemplo: enhancement/improve-search-functionality
Uso: Para realizar mejoras en las funcionalidades ya existentes, que mejoren el rendimiento o la usabilidad.

### refactor/ - Para cambios en el código que no alteran la funcionalidad, pero mejoran la estructura.

Ejemplo: refactor/optimize-query-performance
Uso: Para reorganizar o mejorar el código sin cambiar su comportamiento externo.


## Convenciones de Mensajes de Commit con Corchetes

## [ADD] - Para agregar una nueva funcionalidad.

Ejemplo: [ADD] Add user authentication
Uso: Para introducir nuevas características o funcionalidades en el proyecto.

## [FIX] - Para corregir un error.

Ejemplo: [FIX] Resolve login form validation issue
Uso: Para solucionar errores o bugs identificados en el proyecto.

## [ENH] - Para mejorar funcionalidades existentes.

Ejemplo: [ENH] Improve search performance
Uso: Para mejorar o optimizar funcionalidades ya existentes.

## [REF] - Para cambiar la estructura del código sin alterar su funcionalidad.

Ejemplo: [REF] Optimize database queries
Uso: Para reorganizar o mejorar el código sin cambiar el comportamiento externo.

## [IMP] - Para implementar nuevas funcionalidades o características.

Ejemplo: [IMP] Add user authentication feature
Uso: Para añadir nuevas funciones o características al código.


# Comando para encontrar todos los archivos que contienen variables en español.
## git grep -l "español" 


