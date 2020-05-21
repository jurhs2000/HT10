# Universidad del Valle de Guatemala
# Algoritmos y estructuras de datos
# Hoja de trabajo 10 (parte opcional)
# Julio Roberto Herrera Saban 19402
# Programa que calcula las rutas mas cortas entre ciudades utilizando
# el algoritmo de floyd warshall en un grafo con direcciones

from FloydGraph import *

# Lee el archivo
file = open("guategrafo.txt", "r")
routes = file.readlines()
file.close()

# Recorre las rutas del archivo y las ingresa al grafo
for route in routes:
    string = route.split(" ")
    addEdge(string[0], string[1], string[2])

# Mostrando menu y recibiendo opciones
option = 0
while (option != 4):
    option = input("\nBienvenido al programa de calculo de rutas entre ciudades!\n"+
        "1. Mostrar el valor de la ruta mas corta entre dos ciudades\n"+
        "2. Modificar ruta (Edita, Crea y Elimina)\n"+
        "3. Mostrar la ciudad central\n"+
        "4. Salir\n")
    try:
        option = int(option)
    except:
        print("Ingresa un opcion y numero validos!")
        option = 0
    sourceCity = ""
    targetCity = ""
    distance = -1
    if (option == 1):
        while (not containsVertex(sourceCity)):
            sourceCity = input("Ingresa el nombre de la ciudad de origen:\n")
            if (not containsVertex(sourceCity)):
                print("Ingresa una ciudad que este en el registro")
        while (not containsVertex(targetCity)):
            targetCity = input("Ingresa el nombre de la ciudad de destino:\n")
            if (not containsVertex(targetCity)):
                print("Ingresa una ciudad que este en el registro")
        getMinEdge(sourceCity, targetCity)
    if (option == 2):
        while (not containsVertex(sourceCity)):
            sourceCity = input("Ingresa el nombre de la ciudad de origen:\n")
            if (not containsVertex(sourceCity)):
                print("Ingresa una ciudad que este en el registro")
        while (not containsVertex(targetCity)):
            targetCity = input("Ingresa el nombre de la ciudad de destino:\n")
            if (not containsVertex(targetCity)):
                print("Ingresa una ciudad que este en el registro")
        while (distance < 0):
            distance = input("Ingresa la distancia entre las ciudades (Km)"+
            "(Al ingresar 0 se borrara la ruta):\n")
            try:
                distance = int(distance)
            except:
                print("Ingresa un numero valido")
            if (distance < 0):
                print ("Ingresa un numero valido mayor a 0")
        addEdge(sourceCity, targetCity, distance)
    if (option == 3):
        print("Las ciudades que estan al centro es:")
        getCenter()

if (option == 4):
    print("Gracias por utilizar el programa")