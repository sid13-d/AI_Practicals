import numpy as np

T = np.array([[0.1, 0.1, -1, 1], [0.2, 0.1, -1, 1], [0.5, 0.1, -1, 2], [0.6, 0.1, -1, 2], [0.3, 0.3, -1, 3], [0.4, 0.3, -1, 3]])
c = 10
w = np.array([[-0.1, 0.5, 0.2], [-0.2, 0.11, 0.17], [0.17, 0.16, 0.11]])
D = np.array([[-1, -1, 1], [-1, 1, -1], [1, -1, -1]])

def activation(x):
    if x > 0:
        return 1
    else:
        return -1

def error(x, d):
    x = np.array(x)
    d = np.array(d)
    return np.sum(d-x)

def output(x):
    return activation(x)



def calculate(w, T):
    x = 0
    for i in range(3):
        x += w[i] * T[i]
    return x

def main():
    desired=0
    epoch=0
    while(1):
        e = 0
       
        epoch=epoch+1
        for i in range(6):
            percep = T[i][3]
            augmentedArr = T[i][:3]
            desired
            if percep == 1:
                desired = D[0]
            elif percep == 2:
                desired = D[1]
            else:
                desired = D[2]
            
            y=[]

            for k in range(3):
                net = output(np.dot(augmentedArr,w[k]))
                y.append(net)
            
            print("The final y is ::", y)
            print("The desired is ::", desired)

            print("================:: The original weights are ::==============")
            print(w)

            for l in range(len(w)):
                for m in range(len(w)):
                    w[l][m] += 0.5 * c * (desired[l] - y[l]) * augmentedArr[m]
            
            print("================:: The updated weights are ::==============")
            print(w)

            e += 0.5 * np.sum((desired - y)**2)
            print("the error is :::::::: ", e)
            print("-----------------------------------------------------------------------------")
            print("\n\n")
        if e == 0:
            print("============================Epocs:", i+1)
            break


    print("The final weights")
    print(w)




        
                

if __name__ == "__main__":
    main()


# for i in range(6):
#             y = []
#             d = T[i][3]
#             for j in range(3):
#                 x = calculate(w[j], T[i])
#                 y.append(output(x))
#                 print("The output of perceptron", j, "is", y[j])
            
#             print("the final y is :", y)
            
#             if d ==1:
#                 desired = D[0]
#             elif d == 2:
#                 desired = D[1]
#             else:
#                 desired = D[2]
#             print("And the desired is ::", desired)
#             if np.array_equal(y, desired):
#                 print("The output is correct")
                
#             else:
#                 print("Updating the weight matrix for perceptron", j)
#                 for i in range(len(w)):
#                     for j in range(len(w)):
#                         w[i][j] += 0.5 * c * (desired[i] - y[i]) * T[j][:3]
#                 print("The updated weight matrix is", w)
#                 print("The output is incorrect")
#                 print("The error is", error(y, desired))
#                 e += 0.5 * np.sum((y-desired)**2)
#                 if e == 0:
#                     break