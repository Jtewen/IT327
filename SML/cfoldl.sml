fun cfoldl _ x [] = x
    | cfoldl (func: 'a -> 'b -> 'b) x (h::nil) = func h x
    | cfoldl (func: 'a -> 'b -> 'b) x (h::T) = 
        cfoldl func (func h x) T
