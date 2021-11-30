fun h (x,y) =
    let
        val z = x+1
        fun g w =
            let
                val z = y+1
                fun f x =
                    if (x=0) then 0
                        else z+x+g(w-1)
            in
                if (w=0) then x
                    else z+f(w-1)
            end
    in
        if (x=0) then g y
            else z + g(h(x-1,y))
    end;
