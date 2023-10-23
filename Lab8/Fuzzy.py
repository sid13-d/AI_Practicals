import math
import matplotlib.pyplot as plt

cent_x = 0.5
cent_y = 0.5
gamma = 1

x_values = []
y_values = []
mem_val_values = []
z=0

while (z<10):
    x = float(input("Enter x coordinate "))
    y = float(input("Enter y coordinate: "))
    
    d = math.sqrt((0.5 - x) * (0.5 - x) + (0.5 - y) * (0.5 - y))

    if gamma * d <= 0.1:
        mem_val = 1
    elif 0.1 < gamma * d < 1:
        mem_val = 1 - gamma * d
    elif gamma * d >= 1:
        mem_val = 0
    
    x_values.append(x)
    y_values.append(y)
    mem_val_values.append(mem_val)
    z=z+1
    print(f"membership value {mem_val}")

# Plotting
plt.figure(figsize=(8, 6))
plt.scatter(x_values, y_values, c=mem_val_values, cmap='viridis')
plt.colorbar(label='Membership Value')
plt.xlabel('x')
plt.ylabel('y')
plt.title('Membership Value Plot')
plt.show()