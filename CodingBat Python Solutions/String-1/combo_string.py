def combo_string(a, b):
    s = a
    l = b
      
    if len(s) > len(l):
        s, l = l, s
                
    return s + l + s
