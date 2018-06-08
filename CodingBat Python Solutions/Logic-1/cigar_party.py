def cigar_party(cigars, is_weekend):
    if is_weekend:
        return 40 <= cigars
          
    return 40 <= cigars and cigars <= 60
