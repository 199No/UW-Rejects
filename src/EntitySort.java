package src;

import java.util.ArrayList;

// Java program for implementation of QuickSort
// Adapted from a program by GeeksForGeeks (https://www.geeksforgeeks.org/java-program-for-quicksort/)
class EntitySort
{
    int partition(int a[], ArrayList<Entity> e, int low, int high)
    {
        double pivot = e.get(a[high]).getAbsHitbox().getY(); 
        int i = (low-1);
        for (int j=low; j<high; j++)
        {
          
            // If current element is smaller than or
            // equal to pivot
            if (e.get(a[j]).getAbsHitbox().getY() <= pivot)
            {
                i++;

                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
        }

        int temp = a[i+1];
        a[i+1] = a[high];
        a[high] = temp;

        return i+1;
    }


    /* The main function that implements QuickSort()
      a[] --> Array to be sorted,
      l  --> Starting index,
      h  --> Ending index */
    void sort(int a[], ArrayList<Entity> e, int l, int h)
    {
        if (l < h)
        {
            int pi = partition(a, e, l, h);

            // Recursively sort elements before
            // partition and after partition
            sort(a, e, l, pi-1);
            sort(a, e, pi+1, h);
        }
    }
    
}
