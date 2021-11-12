fun choose 0 _ = [[]]
    | choose _ [] = []
    | choose i (h::T) =
        map (fn x => h::x) (choose (i-1) T) @ choose i T
