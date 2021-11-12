fun worker n i = 
    if (i<n) then 
        if (n mod i = 0) then [i]@(worker n (i+1))
        else (worker n (i+1))
    else nil

fun factorize n = 
    (worker n 1) @ [n] 
