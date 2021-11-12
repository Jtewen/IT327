fun pow (0, _) = 0
    | pow (_, 0) = 1
    | pow (b, e) = b * pow (b, e-1)


fun calc ([], r, i) = 0
    | calc (h::L, r, i) = 
        h * pow (r, i) + calc (L, r, i+1)

fun eval ([], r) = 0
    | eval (L, r) = 
        calc (L, r, 0)
