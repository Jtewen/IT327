fun union ([], []) = nil
    | union ([], hi::Li) = hi::union ([], Li)
    | union (h::L, Li) = h::union (L, Li)
