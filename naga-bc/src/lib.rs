use std::alloc::{alloc as a, dealloc as d, Layout};

#[no_mangle]
pub extern "C" fn alloc(n: usize) -> *mut u8 {
    unsafe { a(Layout::from_size_align(n.max(1), 1).unwrap()) }
}

#[no_mangle]
pub extern "C" fn free(p: *mut u8, n: usize) {
    unsafe { d(p, Layout::from_size_align(n.max(1), 1).unwrap()) }
}

fn run(src: &str, stage: u32) -> Result<String, String> {
    let st = match stage {
        0 => naga::ShaderStage::Vertex,
        1 => naga::ShaderStage::Fragment,
        _ => naga::ShaderStage::Compute,
    };
    let mut fe = naga::front::glsl::Frontend::default();
    let m = fe
        .parse(&naga::front::glsl::Options::from(st), src)
        .map_err(|e| e.emit_to_string(src))?;
    let info = naga::valid::Validator::new(naga::valid::ValidationFlags::all(), naga::valid::Capabilities::all())
        .validate(&m)
        .map_err(|e| e.emit_to_string(src))?;
    naga::back::wgsl::write_string(&m, &info, naga::back::wgsl::WriterFlags::empty()).map_err(|e| e.to_string())
}

#[no_mangle]
pub extern "C" fn conv(p: *const u8, n: usize, stage: u32) -> u64 {
    let s = unsafe { std::str::from_utf8_unchecked(std::slice::from_raw_parts(p, n)) };
    let out = match run(s, stage) {
        Ok(w) => format!("O{}", w),
        Err(e) => format!("E{}", e),
    };
    let b = out.into_bytes().into_boxed_slice();
    let l = b.len();
    let ptr = Box::into_raw(b) as *mut u8 as u64;
    (ptr << 32) | l as u64
}
