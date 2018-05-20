//line 473
#include <iostream>
#include<cstdlib>
#include<cmath>
using namespace std;
int i4_modp(int i,int j)
{
    int value = i%j;
    if(value<0)
    {
        value = value+abs(j);
    }
    return value;
}
int i4_sign(int i)
{
    return i<0?-1:1;
}
int i4_wrap(int ival,int ilo,int ihi)
{
    int jhi,jlo,value,wide;
    jlo = min(ilo,ihi);
    jhi = max(ilo,ihi);
    
    wide = jhi+1-jlo;
    
    if(wide==1)
    {
        value = jlo;
    }
    else
    {
        value = jlo+i4_modp(ival-jlo,wide);
    }
    return value;
}
double r8_epsilon(void)
{
    double r;
    
    r = 1.0;
    while(1.0<(double)(1.0+r))
    {
        r = r/2.0;
    }
    return (2.0*r);
}
int *i4vec_indicator(int n)
{
   int *a;
   int i;
   a = new int[n];
   for(i=0;i<n;i++)
   {
       a[i] = i+1;
   }
   return a;
}
void r82vec_permute(int n,double a[],int p[])
{
    double a_temp[2];
    int i,iget,iput,istart;
    for(istart=1;istart<=n;istart++)
    {
        if(p[istart-1]<0)
        {
            continue;
        }
        else if(p[istart-1]==istart)
        {
            p[istart-1] = -p[istart-1];
            continue;
        }
        else
        {
            a_temp[0] = a[0+(istart-1)*2];
            a_temp[1] = a[1+(istart-1)*2];
            iget = istart;
            for(;;) 
            {
                iput = iget;
                iget = p[iget-1];
                p[iput-1] = -p[iput-1];
                
                if(iget==istart)    
                {
                    a[0+(iput-1)*2] = a_temp[0];
                    a[1+(iput-1)*2] = a_temp[1];
                    break;
                }
                 a[0+(iput-1)*2] =  a[0+(iget-1)*2];
                 a[1+(iput-1)*2] =  a[1+(iget-1)*2]; 
            }
        }
    }
    for(i=0;i<n;i++)
    {
        p[i] = -p[i];
    }
    return;
}
int *r82vec_sort_heap_index_a(int n,double a[]){
    double aval[2];
    int i,*indx,indxt,ir,j,l;
    if(n<1)
    {
        return NULL;
    }
    if(n==1)
    {
        indx = new int[1];
        indx[0] = 1;
        return indx;
    }
    indx = i4vec_indicator(n);
    l = n/2+1;
    ir = n;
    for( ; ; )
    {
        if(1<l)
        {
            l = l-1;
            indxt = indx[l-1];
            aval[0] =  a[0+(indxt-1)*2];
            aval[1] =  a[1+(indxt-1)*2];
            indx[ir-1]= indx[0];
            ir = ir -1;
            if(ir==1)
            {
                indx[0] = indxt;
                break;
            }
        }
        i = l;
        j = l+1;
        while(j<=ir)
        {
            if(j<ir)
            {
                if( a[0+(indx[j-1]-1)*2]<a[0+indx[j]-1]*2||
                (a[0+(indx[j-1]-1)*2]==a[0+(indx[j]-1)*2]&&
                a[1+(indx[j-1]-1)*2]< a[1+(indx[j]-1)*2] ) )
                {
                    j = j+1;
                }
            }
            if( aval[0]<a[0+(indx[j-1]-1)*2]||
            (aval[0]==a[0+(indx[j-1]-1)*2]&&
            aval[1] <a[1+(indx[j-1]-1)*2] ) )
            {
                indx[i-1] = indx[j-1];
                i = j;
                j = j+j;
            }
            else
            {
                j = ir+1;
            }
        }
        indx[i-1]=  indxt;
    }
    return indx;
}
int lrline(double xu,double yu,double xv1,double yv1,double xv2,double yv2,double dv)
{
    double dx,dxu,dy,dyu,t,tol = 0.0000001,tolabs;
    int value;
    dx = xv2-xv1;
    dy = yv2-yv1;
    dxu = xu-xv1;
    dyu= yu-yv1;
    
    tolabs= tol*max(fabs(dx),
                max(fabs(dy),
                max(fabs(dxu),
                max(fabs(dyu),fabs(dv) ) ) ) );
        
    t = dy*dxu-dx*dyu+dv+sqrt(dx*dx+dy*dy);
    if(tolabs < t)
    {
        value = 1;
    }
    else if(-tolabs<=t)
    {
        value =0;
    }
    else if(t<-tolabs)
    {
        value = -1;
    }
    return value;
}
void vbedg(double x,double y,int point_num,double point_xy[],int tri_num,
int tri_vert[],int tri_nabe[],int *ltri,int *ledg,int *rtri,int *redg)
{
    int a;
    double ax;
    double ay;
    int b;
    double bx;
    double by;
    bool done;
    int e,l,lr,t;
    if(ltri==0)
    {
        done = false;
        *ltri = *rtri;
        *ledg = *redg;
    }
    else
    {
        done = true;
    }
    for(; ;)
    {
        l = -tri_nabe[3*((*rtri)-1)+(*redg)-1];
        t = l/3;
        e = 1+l%3;
        a = tri_vert[3*(t-1)+e-1];
        
        if(e<=2)
        {
            b = tri_vert[3*(t-1)+e];
        }
        else
        {
            b = tri_vert[3*(t-1)+0];
        }
        ax = point_xy[2*(a-1)+0];
        ay = point_xy[2*(a-1)+1];
        
        bx = point_xy[2*(b-1)+0];
        by = point_xy[2*(b-1)+1];
        
        lr = lrline(x,y,ax,ay,bx,by,0.0);
        
        if(lr<=0)
        {
            break;
        }
        *rtri = t;
        *redg = e;
    }
    if(done)
    {
        return;
    }
    t = *ltri;
    e = *ledg;
    for(;;)
    {
        b = tri_vert[3*(t-1)+e-1];
        e = i4_wrap(e-1,1,3);
        while(0<tri_nabe[3*(t-1)+e-1])
        {
            t = tri_nabe[3*(t-1)+e-1];
            
            if(tri_vert[3*(t-1)+0]==b)
            {
                e = 3;
            }
            else if(tri_vert[3*(t-1)+1]==b)
            {
                e = 1;
            }
            else
            {
                e = 2;
            }
        }
        a = tri_vert[3*(t-1)+e-1];
        ax = point_xy[2*(a-1)+0];
        ay = point_xy[2*(a-1)+1];
        
        bx = point_xy[2*(b-1)+0];
        by = point_xy[2*(b-1)+1];
        
        lr = lrline(x,y,ax,ay,bx,by,0.0);
        if(lr<=0)
        {
            break;
        }
    }
    *ltri = t;
    *ledg = e;
    
    return;
}
int diaedg ( double x0,double y0, double x1, double y1, double x2, double y2, double x3, double y3)
{
 double ca,cb,dx10,dx12,dx30,dx32,dy10,dy12,dy30,dy32,s,tol,tola,tolb;
 int value;
    tol = 100.0*r8_epsilon();
    
    dx10 = x1-x0;
    dy10 = y1- y0;
    dx12 = x1- x2;
    dy12 = y1 - y2;
    dx30 = x3 - x0;
    dy30 = y3 - y0;
    dx32 = x3 - x2;
    dy32 = y3 - y2;
    tola = tol*max(fabs(dx10),
    max(fabs(dy10),
    max(fabs(dx30),fabs(dy30))));
    
    tolb = tol*max(fabs(dx12),
               max(fabs(dy12),
               max(fabs(dx32),fabs(dy32))));
               
    ca = dx10*dx30 + dy10*dy30;
    cb = dx12*dx32 + dy12*dy32;
    
    if(tola<ca&&tolb<cb)
    {
        value = -1;
    }
    else if(ca<-tola&&cb<-tolb)
    {
        value = 1;
    }
    else
    {
        tola = max(tola,tolb);
        s = (dx10*dy30-dx30*dy10)*cb+
        (dx32&dy12-dx12*dy32)*ca;
        if(tola<s)
        {
            value = -1;
        }
        else if(s<-tola)
        {
            value = 1;
        }
        else
        {
            value =0;
        }
    }
    return value;   
}
int main()
{ 
   cout << "Hello World!" << endl;
   
   return 0;
}
